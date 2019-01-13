<template>
    <div id="su-history">
        <div class="su-history-date">
            <button v-on:click="prevTab" :class="{grayed: noPrev}"> < </button>
            <p @click="showCalendar = !showCalendar">{{getDisplayDate(currentDate)}}</p>
            <button v-on:click="nextTab" :class="{grayed: noNext}"> > </button>
            <div class="su-history-calendar" v-if="showCalendar">
                <p class="su-calendar-title">Pick a day</p>
                <p v-for="date in dates" @click="toDate(date)">
                    {{getDisplayDate(date)}}
                </p>
            </div>
        </div>
        <div class="su-history-container">
            <div class="su-history-line-box">
                <history-line v-for="timeSlot in historyTabs[currentDate][0]" :key="timeSlot[1]">
                    <template slot="timeLabel">{{timeSlot[1]}}</template>
                    <template slot="lineItems">
                        <history-line-item v-for="card in timeSlot[0]" :key="card.orderId" :order-id="card.orderId">

                        </history-line-item>
                    </template>
                </history-line>
            </div>

            <history-tab v-for="(tab, key) in historyTabs" v-if="key === String(currentDate)">
                <template slot="historyTimeSections">
                    <history-section v-for="timeSlot in tab[0]">
                        <template slot="timeLabel">{{timeSlot[1]}}</template>
                        <template slot="historyCards">
                            <history-card v-for="card in timeSlot[0]" :key="card.orderId" :order-id="card.orderId">
                                <template slot="arrivalTime">{{card.displayTime}}</template>
                                <template slot="ownerName"></template>
                                <template slot="priceTotal">{{card.priceTotal}}</template>

                                <template slot="historyItems">
                                    <history-card-item v-for="item in card.orderItems" :key="item.id">
                                        <template slot="amount">{{item.amount}}</template>
                                        <template slot="foodName">{{item.name}}</template>
                                        <template slot="price">{{item.price}}</template>
                                    </history-card-item>
                                </template>
                            </history-card>
                        </template>
                    </history-section>
                </template>
            </history-tab>
        </div>
    </div>
</template>

<script>
    import HistoryTab from "../components/history/HistoryTab";
    import HistorySection from "../components/history/HistorySection";
    import HistoryCard from "../components/history/HistoryCard";
    import HistoryCardItem from "../components/history/HistoryCardItem";
    import axios from "axios";
    import {EventBus} from "../Events.js";
    import {serverUrl} from "../Events";
    import HistoryLine from "../components/history/HistoryLine";
    import HistoryLineItem from "../components/history/HistoryLineItem";
    import VueScrollTo from "vue-scrollto/src/directive";

    export default {
        name: "History",
        components: {HistoryLineItem, HistoryLine, HistoryCardItem, HistoryCard, HistorySection, HistoryTab},

        data() {
            return {
                monthNames: ['Jan', 'Feb', 'Mar', 'Apr', 'Maj', 'Jun', 'Jul', 'Avg', 'Sep', 'Okt', 'Nov', 'Dec'],
                orderCards: [],
                dates: [],
                currentDate: new Date(),
                noPrev: false,
                noNext: true,
                showCalendar: false,
                scrollOptions: {
                    container: '#su-time-section-box', // element to scroll
                    easing: 'ease-in',
                    offset: -60,
                    force: true, // force scroll even if the object is in view
                    cancelable: true,
                    onStart: function(element) {
                        //console.log('started scrolling')
                    },
                    onDone: function(element) {
                        //console.log('stopped scrolling')
                    },
                    onCancel: function() {
                        // scrolling has been interrupted
                    },
                    x: false,
                    y: true
                },
            }
        },

        computed: {
            historyTabs: function () {
                let historyTabs = {};
                let date = new Date();
                let self = this;
                let len = this.orderCards.length;
                this.orderCards.forEach(function (orderCard, i) {
                    let fullTime = orderCard.arrivalTime;// The time slot in which the card fits
                    fullTime.setSeconds(0);

                    // Decides in which time slot the card fits
                    // This can be changed depending on how long the time slots need to be
                    if (fullTime.getMinutes() < 30) {
                        fullTime.setMinutes(0);
                    }
                    else {
                        fullTime.setMinutes(30);
                    }
                    //let month = fullTime.getMonth()+1;

                    //let date = "" + fullTime.getDate() + month + fullTime.getFullYear();
                    date = new Date(fullTime);
                    date.setHours(0);
                    date.setMinutes(0);

                    // If the time slot doesn't exist, make a new one
                    if (!(date in historyTabs)) {
                        if (i === 0) {
                            historyTabs[date] = [[], true];//{timeSlots: [], isActive: true};
                            self.dates.push(date);
                            self.currentDate = date;
                        }
                        else {
                            historyTabs[date] = [[], false];//{timeSlots: [], isActive: true};
                            self.dates.push(date)
                        }
                    }

                    historyTabs[date][0].unshift(orderCard);
                    //console.log(historyTabs);
                });

                Object.keys(historyTabs).forEach(key => {
                    let timeSlots = {};
                    historyTabs[key][0].forEach(orderCard => {
                        let fullTime = orderCard.arrivalTime;// The time slot in which the card fits
                        fullTime.setSeconds(0);

                        // Decides in which time slot the card fits
                        // This can be changed depending on how long the time slots need to be
                        if (fullTime.getMinutes() < 30) {
                            fullTime.setMinutes(0);
                        }
                        else {
                            fullTime.setMinutes(30);
                        }

                        // If the time slot doesn't exist, make a new one
                        if (!(fullTime in timeSlots)) {
                            timeSlots[fullTime] = [[/*New*/], this.getDisplayTime(fullTime)];
                        }

                        // Assign the order card to the correct column based on its status
                        timeSlots[fullTime][0].unshift(orderCard);
                    });
                    historyTabs[key][0] = timeSlots;
                });
                //console.log(self.dates[self.dates.length-1].$set('visible', true));
                return historyTabs;
            }
        },

        methods: {
            parseOrder(unparsedOrder) {
                let parsedOrder = {
                    orderId: unparsedOrder.id_narocila,
                    orderStatus: unparsedOrder.status,
                    isHere: unparsedOrder.checked_in,
                    arrivalTime: new Date(unparsedOrder.cas_prevzema),
                    displayTime: "",
                    ownerName: unparsedOrder.id_uporabnik,
                    priceTotal: unparsedOrder.cena,
                    totalPrepTime: 0,
                    isHighlighted: false,
                    // TODO parse the order items as well
                    orderItems: [/*{id: 0, amount: 10, name: "Pizza", prepTime: 20}, {id: 1, amount: 19, name: "Taco", prepTime: 20},{id: 3, amount: 19, name: "Taco", prepTime: 20}*/]
                };

                // set the time that will be displayed in the DOM
                parsedOrder.displayTime = this.getDisplayTime(parsedOrder.arrivalTime);

                // parse the orderItems
                Object.keys(unparsedOrder.jedi).forEach(indexJedi => {
                    let unparsedItem = unparsedOrder.jedi[indexJedi];
                    parsedOrder.totalPrepTime += unparsedItem.cena; // TODO prep time namesto cena
                    parsedOrder.orderItems.push({
                        id: unparsedItem.id_jed,
                        amount: unparsedItem.kolicina,
                        name: unparsedItem.ime_jedi,
                        prepTime: unparsedItem.cena,
                        price: unparsedItem.cena// TODO prep time namesto cena
                    });
                });

                return parsedOrder;
            },

            toDate(date) {
                this.currentDate = date;
                this.showCalendar = false;
            },

            prevTab() {
                for (let i = 0; i < this.dates.length; i++) {
                    if (this.dates[i] === this.currentDate && i < this.dates.length-1) {
                        this.currentDate = this.dates[i+1];
                        this.noNext = false;
                        if (i+1 === this.dates.length - 1) {
                            this.noPrev = true;
                        }
                        else {
                            this.noPrev = false;
                        }
                        break;
                    }
                }
            },

            nextTab() {
                for (let i = 0; i < this.dates.length; i++) {
                    if (this.dates[i] === this.currentDate && i > 0) {
                        this.currentDate = this.dates[i-1];
                        this.noPrev = false;
                        if (i-1 === 0) {
                            this.noNext = true;
                        }
                        else {
                            this.noNext = false;
                        }
                        break;
                    }
                }
            },

            getDisplayDate(time) {
                if (time === null) {
                    return 'TIME'
                }
                return "" + time.getDate() + ". " + this.monthNames[time.getMonth()] + ' ' + time.getFullYear();
            },

            getDisplayTime(time) {
                if (time === null) {
                    return 'TIME'
                }
                return "" + time.getHours() + ":" + ('0' + time.getMinutes()).slice(-2);
            },
        },

        created() {
            let self = this;
            let rId =  this.$session.get('restaurantId');
            axios.get(serverUrl + 'orders/?id_restavracija=' + rId)
                .then(function (response) {
                    // TODO remove log
                    console.log('Response data', response.data.data);
                    // Parse each card from the server response data and insert the parsed order
                    // in the view's orderCards dict
                    Object.keys(response.data.data).forEach(objectId => {
                        if (response.data.data[objectId].status === 3) {
                            let parsedOrder = self.parseOrder(response.data.data[objectId]);
                            self.orderCards.push(parsedOrder);
                        }
                    });

                    self.orderCards.sort(function (card1, card2) {
                        return card2.arrivalTime - card1.arrivalTime;
                    });
                });
        },

        mounted() {
            let orderCards = this.orderCards;
            let scrollOptions = this.scrollOptions;

            EventBus.$on('highlightHis', function(orderId) {
                let clickedOrderCard = orderCards.filter(orderCard => {
                    return orderCard.orderId === orderId;
                })[0];
                clickedOrderCard.isHighlightedH = true;
                let scrollId = '#su-card-h-' + orderId; // the ID of the object we scroll to
                //VueScrollTo.scrollTo(scrollId, 200, scrollOptions);
                // The user has to wait 1 second, before they can highlight the item again
                /*setTimeout(function () {
                    clickedOrderCard.isHighlighted = false;
                    console.log("timeout");
                }, 1000);*/
            });
        },

        beforeDestroy() {
            EventBus.$off('highlight')
            //console.log('destroyed');
        }
    }
</script>

<style lang="scss" scoped>
    @import "../styles/variables";

    #su-history {
        background: $su-color-background;
        display: flex;
        flex-direction: column;
        width: 100%;
        height: 100%;

        .su-history-date {
            background: $su-color-content-light;
            margin-bottom: 8px;
            box-shadow: $su-shadow;
            justify-content: center;
            align-items: center;
            display: flex;
            flex-shrink: 0;

            .su-history-calendar {
                position: absolute;
                padding: 1rem;
                padding-top: 0rem;
                top: 4rem;
                width: 20rem;
                background: $su-color-content-light;
                z-index: 100;
                border-radius: 8px;
                box-shadow: 0 16px 85px 17px rgba(0,0,0,0.35);
            }

            p {
                margin: 0.5rem;
                padding: 0.5rem 1.5em 0.5rem 1.5rem;
                background: $su-color-secondary;
                color: $su-color-content-light;
                box-shadow: $su-shadow;
                border: none;
                border-radius: $su-border-radius-m;
                cursor: pointer;

                &:hover {
                    box-shadow: $su-shadow-hover;
                }

                &:active {
                    background: $su-color-secondary-dark;
                }

                &:focus {
                    outline:0;
                }
            }

            .su-calendar-title {
                font-size: 1.2rem;
                background: none;
                box-shadow: none;
                color: $su-color-dark-gray;
                cursor: default;

                &:hover {
                    box-shadow: none;
                }

                &:active {
                    background: none;
                }

                &:focus {
                    outline:0;
                }
            }

            button {
                cursor: pointer;
                font-weight: bold;
                padding: 0.5rem 1rem;
                background: $su-color-secondary;
                color: $su-color-content-light;
                box-shadow: $su-shadow;
                border: none;
                border-radius: $su-border-radius-m;

                &:hover {
                    box-shadow: $su-shadow-hover;
                }

                &:active {
                    background: $su-color-secondary-dark;
                }

                &:focus {
                    outline:0;
                }
            }

            .grayed {

            }
        }

        .su-history-container {
            display: flex;
            width: 100%;
            height: 100%;
            z-index: 10;
            box-shadow: $su-shadow;
            min-height:0;
        }

        .su-history-line-box {
            width: 20rem;
            flex: 1 0 max-content;
            background: $su-color-primary-pale;
            overflow-y: scroll;
            overflow-x: hidden;
        }

    }
</style>