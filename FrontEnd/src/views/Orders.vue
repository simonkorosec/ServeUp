<template>
<div id="su-orders">
    <div class="su-top-bar">
        <div class="pregled">
            <img src="../assets/icons/overview.svg">
            <p>Pregled</p>
        </div>
        <div>
            <img src="../assets/icons/new.svg">
            <p>Nova naročila</p>
        </div>
        <div>
            <img src="../assets/icons/making.svg">
            <p>V pripravi</p>
        </div>
        <div>
            <img src="../assets/icons/ready.svg">
            <p>Pripravljeno</p>
        </div>
    </div>
    <div id="su-orders-container">
        <div id="su-time-line-box">
            <time-line v-for="(timeSlot, time) in timeSlots" :key="time">
                <template slot="timeLabel">{{timeSlot[3]}}</template>

                <template slot="sectionNew" class="su-time-line-item-new">
                    <time-line-item v-for="card in timeSlot[0]" :key="card.orderId"
                                    :order-id="card.orderId"
                                    :class="{suHere: card.isHere}"
                                    class="su-time-line-item-new">
                    </time-line-item>
                </template>

                <template slot="sectionMaking" class="su-time-line-item-making">
                    <time-line-item v-for="card in timeSlot[1]" :key="card.orderId"
                                    :order-id="card.orderId"
                                    :class="{suHere: card.isHere}"
                                    class="su-time-line-item-making">
                    </time-line-item>
                </template>

                <template slot="sectionReady" class="su-time-line-item-new">
                    <time-line-item v-for="card in timeSlot[2]" :key="card.orderId"
                                    :order-id="card.orderId"
                                    :class="{suHere: card.isHere}"
                                    class="su-time-line-item-ready">
                    </time-line-item>
                </template>
            </time-line>
            <div class="su-time-section-footer" v-if="!isEmpty(orderCards)"></div>
        </div>
        <div id="su-time-section-box">
            <div v-if="noOrders">
                <p class="su-no-orders">Trenutno ni naročil</p>
            </div>
            <!--For each timeSlot generate a new time section-->
            <time-section v-for="(timeSlot, time) in timeSlots" :key="time">
                <!--timeSlot[3] is the display time-->
                <template slot="timeLabel">{{timeSlot[3]}}</template>

                <template slot="sectionNew">
                    <!--For each orderCard in that time section generate a new order card-->
                    <order-card v-for="card in timeSlot[0]" :key="card.orderId"
                                :order-id="card.orderId"
                                :class="{suHereNew: card.isHere, highlighted: card.isHighlighted}"
                                :id="'su-card-' + card.orderId">
                        <template slot="arrivalTime">{{card.displayTime}}</template>
                        <template slot="ownerName">{{card.tableName}}</template>
                        <template slot="priceTotal">{{card.priceTotal}}</template>

                        <!--For each order item in the orderCard generate a new order card item-->
                        <template slot="orderItems">
                            <order-card-item v-for="item in card.orderItems" :key="item.id">
                                <template slot="amount">{{item.amount}}</template>
                                <template slot="foodName">{{item.name}}</template>
                                <template slot="prepTime">{{item.prepTime}}</template>
                            </order-card-item>
                        </template>

                        <template slot="totalPrepTime">{{card.totalPrepTime}}</template>
                    </order-card>
                </template>

                <template slot="sectionMaking">
                    <!--For each orderCard in that time section generate a new order card-->
                    <order-card v-for="card in timeSlot[1]" :key="card.orderId"
                                :order-id="card.orderId"
                                :class="{suHereMaking: card.isHere, highlighted: card.isHighlighted}"
                                :id="'su-card-' + card.orderId">
                        <template slot="arrivalTime">{{card.displayTime}}</template>
                        <template slot="ownerName">{{card.tableName}}</template>
                        <template slot="priceTotal">{{card.priceTotal}}</template>

                        <!--For each order item in the orderCard generate a new order card item-->
                        <template slot="orderItems">
                            <order-card-item v-for="item in card.orderItems" :key="item.id" >
                                <template slot="amount">{{item.amount}}</template>
                                <template slot="foodName">{{item.name}}</template>
                                <template slot="prepTime">{{item.prepTime}}</template>
                            </order-card-item>
                        </template>

                        <template slot="totalPrepTime">{{card.totalPrepTime}}</template>
                    </order-card>
                </template>

                <template slot="sectionReady">
                    <!--For each orderCard in that time section generate a new order card-->
                    <order-card v-for="card in timeSlot[2]" :key="card.orderId"
                                :order-id="card.orderId"
                                :class="{suHereReady: card.isHere, highlighted: card.isHighlighted}"
                                :id="'su-card-' + card.orderId">
                        <template slot="arrivalTime">{{card.displayTime}}</template>
                        <template slot="ownerName">{{card.tableName}}</template>
                        <template slot="priceTotal">{{card.priceTotal}}</template>

                        <!--For each order item in the orderCard generate a new order card item-->
                        <template slot="orderItems">
                            <order-card-item v-for="item in card.orderItems" :key="item.id">
                                <template slot="amount">{{item.amount}}</template>
                                <template slot="foodName">{{item.name}}</template>
                                <template slot="prepTime">{{item.prepTime}}</template>
                            </order-card-item>
                        </template>

                        <template slot="totalPrepTime">{{card.totalPrepTime}}</template>
                    </order-card>
                </template>
            </time-section>
            <div class="su-time-section-footer" v-if="!isEmpty(orderCards)"></div>
        </div>
    </div>
</div>
</template>

<script>
import TimeSection from "../components/orders/TimeSection";
import OrderCard from "../components/orders/OrderCard";
import OrderCardItem from "../components/orders/OrderCardItem";
import {EventBus} from "../Events.js";
import {serverUrl} from "../Events";
import axios from "axios";
import TimeLine from "../components/orders/TimeLine";
import TimeLineItem from "../components/orders/TimeLineItem";
import VueScrollTo from "vue-scrollto/src/directive";
import _ from 'lodash';

export default {
    name: "Orders",

    components: {TimeLineItem, TimeLine, OrderCardItem, OrderCard, TimeSection},

    data() {
        return {
            orderCards: [],
            // How often to refresh the page after the initial load, refresh function located in mounted
            refreshInterval: 5000, // milliseconds
            // scrollOptions defines the way the scrolling behaves when clicking on
            // a card on the TimeLine
            refreshTimer: null,

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
        // Builds an array of time slots out of orders
        // Structure: timeSlots{timeSlot1[New][Cooking][Ready], timeSlot2[New][Cooking][Ready], ...}
        noOrders: function () {
            if(this.orderCards.length === 0) {
                return true
            }
            return false
        },

        timeSlots: function () {
            let timeSlots = {};
            this.orderCards.forEach(orderCard => {
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
                    timeSlots[fullTime] = [[/*New*/], [/*Cooking*/], [/*Ready*/], this.getDisplayTime(fullTime)];
                }

                // Assign the order card to the correct column based on its status
                timeSlots[fullTime][orderCard.orderStatus].unshift(orderCard);
            });

            return timeSlots;
        }
    },

    methods: {
        // Check if the object is empty
        isEmpty(obj) {
            for(let key in obj) {
                if(obj.hasOwnProperty(key))
                    return false;
            }
            return true;
        },

        parseOrder(unparsedOrder) {
            let cas_prevzema = unparsedOrder.cas_prevzema;
            if (cas_prevzema.slice(-1) === "Z") {
                cas_prevzema = cas_prevzema.slice(0, -1);
            }
            console.log(unparsedOrder);
            let parsedOrder = {
                orderId: unparsedOrder.id_narocila,
                orderStatus: unparsedOrder.status,
                isHere: unparsedOrder.checked_in,
                arrivalTime: new Date(cas_prevzema),
                displayTime: "",
                ownerName: unparsedOrder.id_uporabnik,
                priceTotal: unparsedOrder.cena.toFixed(2),
                totalPrepTime: 0,
                isHighlighted: false,
                tableName: '',
                orderItems: []
            };

            // set the time that will be displayed in the DOM
            parsedOrder.displayTime = this.getDisplayTime(parsedOrder.arrivalTime);
            
            if (unparsedOrder.id_miza !== null && typeof unparsedOrder.id_miza !== 'undefined') {
                parsedOrder.tableName = unparsedOrder.id_miza;
            }
            else {
                parsedOrder.tableName = "Check in";
            }
            
            // parse the orderItems
            Object.keys(unparsedOrder.jedi).forEach(indexJedi => {
                let unparsedItem = unparsedOrder.jedi[indexJedi];
                let tempPrep = Math.floor((Math.random() * (20 - 5) + 5));
                parsedOrder.totalPrepTime += tempPrep; // TODO prep time namesto cena
                parsedOrder.orderItems.push({
                    id: unparsedItem.id_jed,
                    amount: unparsedItem.kolicina,
                    name: unparsedItem.ime_jedi,
                    prepTime: tempPrep,
                    price: unparsedItem.cena
                });
            });

            return parsedOrder;
        },

        updateOrders () {
            let self = this;
            let orderirir = this.orderCards;
            let rId = this.$session.get('restaurantId');
            axios.get(serverUrl + 'orders/refresh/?id_restavracija=' + rId)
                .then(function (response) {
                        //self = this;
                        console.log('Refresh data', response.data);
                        if (response.data.new_orders.length !== 0){
                            // TODO remove log
                            //console.log(self.orderCards);
                            console.log('Refresh data rcv', response.data);
                            // Parse each card from the server response data and insert the parsed order
                            // in the view's orderCards dict
                            Object.keys(response.data.new_orders).forEach(objectId => {
                                let parsedOrder = self.parseOrder(response.data.new_orders[objectId]);
                                self.orderCards.push(parsedOrder);
                            });

                            self.orderCards.sort(function (card1, card2) {
                                return card1.arrivalTime - card2.arrivalTime;
                            });
                        }
                        if (response.data.cancelled_orders.length !== 0) {
                            console.log('Cancelled', response.data);
                            for (let i = self.orderCards.length - 1; i >= 0; i--) {
                                if (response.data.cancelled_orders.includes(self.orderCards[i].orderId)) {
                                    self.orderCards.splice(i, 1);
                                }
                            }
                        }

                        // TODO checked in users
                        if (response.data.checked_in_orders.length !== 0) {
                            let checkedIn = response.data.checked_in_orders;
                            for (let i = self.orderCards.length - 1; i >= 0; i--) {
                                for (let j = 0; j < checkedIn.length; j++) {
                                    if (checkedIn[j].id_narocila === self.orderCards[i].orderId) {
                                        self.orderCards[i].isHere = true;
                                        self.orderCards[i].tableName = checkedIn[j].qr;
                                    }
                                }
                            }
                        }
                    }
                ).catch(function (error) {
                console.log('refresh error', error);
            });
        },

        // Extracts the hour and minute from the full date,
        // so it can be displayed in the DOM
        getDisplayTime(time) {
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
                    if (response.data.data[objectId].status !== 3) {
                        let parsedOrder = self.parseOrder(response.data.data[objectId]);
                        self.orderCards.push(parsedOrder);
                    }
                });

                self.orderCards.sort(function (card1, card2) {
                    return card1.arrivalTime - card2.arrivalTime;
                });
            }
        );
    },

    mounted() {
        // the this of the vue object is not accessible inside other functions
        // in order to be acceded it must be stored in a variable
        let orderCards = this.orderCards;
        let scrollOptions = this.scrollOptions;

        // Unbind all events from event bus, so they don't trigger multiple times
        EventBus.$off();

        // When a status of the card is changed intercept the event
        // and move the card in the next status group (New => Cooking => Ready => Done)
        EventBus.$on('changeStatus', orderId => {
            let clickedOrderCard = null;
            let orderIndex = -1;

            orderCards.forEach(function (orderCard, i) {
                if (orderCard.orderId === orderId) {
                    clickedOrderCard = orderCard;
                    orderIndex = i;
                }
            });

            // If the current status is Ready, remove the card from the list
            if (clickedOrderCard.orderStatus === 2) {
                orderCards.splice(orderIndex, 1);
                //this.$delete(self.orderCards, orderId);
                //self.orderCards
            }
            // Move it in the next status group
            clickedOrderCard.orderStatus += 1;

            // Update the server
            axios.post(serverUrl + 'orders/status_update/', {
                id_narocilo: clickedOrderCard.orderId,
                status: clickedOrderCard.orderStatus
            }).then(function (response) {
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            });
        });

        // Determine what happens when the user highlights the tiny card in the TimeLine
        EventBus.$on('highlight', function(orderId) {
            let clickedOrderCard = orderCards.filter(orderCard => {
                return orderCard.orderId === orderId;
            })[0];
            clickedOrderCard.isHighlighted = true;
            let scrollId = '#su-card-' + orderId; // the ID of the object we scroll to
            VueScrollTo.scrollTo(scrollId, 200, scrollOptions);
            // The user has to wait 1 second, before they can highlight the item again
            setTimeout(function () {
                clickedOrderCard.isHighlighted = false;
                console.log("timeout");
            }, 1000);
        });

        // TODO Periodically refresh the page with new orders from the server
        let self = this;
        this.refreshTimer = setInterval(function () {
            self.updateOrders();
        }, self.refreshInterval);
    },

    beforeDestroy() {
        clearInterval(this.refreshTimer);
        EventBus.$off('changeStatus');
        EventBus.$off('highlight')
        //console.log('destroyed');
    }
}
</script>

<style lang="scss" scoped>
    @import "../styles/variables";

    #su-orders {
        display: flex;
        flex-direction: column;
        width: 100%;
        height: 100%;
        background: $su-color-background;

        .su-top-bar {
            background: $su-color-content-light;
            align-items: stretch;
            display: inline-flex;
            flex-shrink: 0;
            margin-bottom: 8px;
            box-shadow: $su-shadow;

            div {
                flex-grow: 3;
                display: flex;
                justify-content: center;

                img {
                    margin: auto 0.5rem auto -4rem;
                    width: 1.2rem;
                    height: 1.2rem;
                }

                p {
                    font-weight: bold;
                    color: $su-color-dark-gray;
                }
            }

            .pregled {
                flex-grow: 1;

                img {
                    margin-left: -1rem;
                }
            }
        }

        #su-orders-container {
            display: flex;
            width: 100%;
            height: 100%;
            z-index: 10;
            box-shadow: $su-shadow;
            min-height:0;

            #su-time-line-box {
                flex: 1 0 max-content;
                background: $su-color-primary-pale;
                overflow-y: scroll;
                overflow-x: hidden;
            }

            #su-time-section-box {
                flex: 8 1 auto;
                overflow-y: auto;
                overflow-x: hidden;

                .su-no-orders {
                    font-size: 2rem;
                }
            }
        }
    }

    .su-time-section-footer {
        position: sticky;
        background: $su-color-content-light;
        box-shadow: $su-shadow-top;
        z-index: 10;
        width: 100%;
        height: 8px;
        top: 0;
        left: 0;
    }
</style>