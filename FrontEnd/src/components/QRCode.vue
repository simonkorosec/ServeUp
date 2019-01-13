<template>
    <div class="container" id="qr-container" :class="{printContainer: printMode}">
        <input class="qrtext" v-model="value" type="text" placeholder="Vnesi ime mize"/>
        <button class="insert"  type="button" v-on:click="name()">Generiraj QR kodo</button>
        <button class="insert" type="button" v-on:click="printElem">Natisni</button>
        <br>
        <div class="qr-code-container">
            <QR class="qr-code-item" v-for="item in items" :value="item" > </QR>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {serverUrl} from "../Events";
    import QrcodeVue from 'qrcode.vue';
    import QR from "./QR";
    export default {
        name: 'QRCode',
        data() {
            return {
                items: [],
                value:'',
                printMode: false,
            }

        },
        components: {
            QR,
            QrcodeVue
        },
        beforeCreate: function () {
            try{
                if (this.$session.exists()) {
                    console.log('obstaja');
                }
                else{
                    this.$router.push({ name: "login" });
                }
            }
            catch (e) {
                this.$router.push({ name: "login" });
            }
        },
        mounted: function () {
            if (this.$session.exists()) {
                console.log('obstaja');
            }
            else{
                console.log('ne obstaja');
            }
            axios.get(serverUrl + 'restaurant/fetch_qr/?id_restavracija='+this.$session.get("restaurantId"), {
            }).then(response => {
                this.items = response.data.data;
                console.log(this.items);
            }).catch(error => {
                console.log(error);
            });
        },
        methods:{
            /*add:function () {
                if (this.value !== "") {
                    this.items.push(this.value);
                }
            },*/
            name:function() {
                console.log(this.$session.get("restaurantId"));
                axios.post(serverUrl + 'restaurant/add_table/', {
                    id_restavracija: this.$session.get("restaurantId"),
                    qr: this.value
                }).then(response => {
                    console.log(response.status);
                }).catch(error => {
                    console.log(error);
                });

                setTimeout(function () {
                    axios.get(serverUrl + 'restaurant/fetch_qr/?id_restavracija='+this.$session.get("restaurantId"), {
                    }).then(response => {
                        this.items = response.data.data;
                        console.log(this.items);
                    }).catch(error => {
                        console.log(error);
                    });
                }.bind(this), 1000);
            },
            printElem() {
                for (let i = 0; i < 2; i++) {

                }
                let mywindow = window.open('', 'PRINT', 'height=842,width=1000,titlebar=no');
                let qrCodes = document.getElementsByTagName("canvas");
                let qrImages = [];

                let body = mywindow.document.body;
                // let title = document.createElement("P");
                // let t = document.createTextNode("Pritisni Ctrl+P za print");
                // title.style.fontSize = '16px';
                // title.style.margin = '8px';
                // title.style.color = 'gray';
                // title.appendChild(t);
                //
                // body.appendChild(title);

                for (let i = 0; i < qrCodes.length; i++) {
                    let tableName = qrCodes[i].parentElement.parentElement.querySelector('#printMe>b').innerHTML;
                    let para = document.createElement("P");
                    para.style.fontSize = '32px';
                    para.style.margin = '16px';
                    para.style.color = 'gray';
                    let text = document.createTextNode(String(tableName));
                    para.appendChild(text);
                    body.appendChild(para);

                    let qrImage = document.createElement('img');
                    let src = qrCodes[i].toDataURL();
                    qrImage.src = src;
                    qrImage.style.padding = '32px';
                    qrImage.style.border = '2px solid lightgray';
                    qrImage.style.boxSizing = 'border-box';
                    qrImage.style.height = "421px";
                    body.appendChild(qrImage);
                }

                mywindow.document.close();
                mywindow.focus();

                mywindow.print();
                mywindow.print();
                mywindow.close();

                return true;
            }
        }

    }
</script>

<style lang="scss" scoped>
    @import "../styles/variables";

.container{
    background: $su-color-background;
    text-align: left;
    overflow-x: hidden;
    overflow-y: scroll;
    width:100%;
}
.qrtext{
    width:300px;
    color: $su-color-dark-gray;
    position: -webkit-sticky;
    position: sticky;
    background: $su-color-content-light;
    top: 20px;
    left: 20px;
    border: 2px solid $su-color-light-gray;
    outline:none;
    box-shadow: $su-shadow;
}
.qr-code-container {
    text-align: center;
    margin-top: 50px;
}

.insert{
    width:200px;;
    position: -webkit-sticky;
    position: sticky;
    top: 20px;
    margin-left: 35px;
    cursor: pointer;
    box-shadow: $su-shadow;

    &:active {
        background: $su-color-primary-dark;
    }

    &:focus {
        outline:0;
    }
}

</style>