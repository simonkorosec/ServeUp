<template >
    <div class="qrticket">
        <label id="printMe"><b>{{value}}</b></label><br>
        <qrcode-vue  :value="value" :size="size" level="H"></qrcode-vue>

        <!--<button type="button" v-print>Natisni QR kodo</button>-->
    </div>

</template>

<script>
    import QrcodeVue from 'qrcode.vue';
    export default {
        name: "QR",
        props: {
            value:String
        },
        data() {
            return {
                size: 200,
                items:[],
            }
        },
        components: {
            QrcodeVue
        },
        methods:{
            del:function (n) {
                if (localStorage.items) {
                    this.items = JSON.parse(localStorage.getItem("items"));
                    this.items.splice(this.items.indexOf(this.value), 1);
                    localStorage.setItem("items", JSON.stringify(this.items));
                    this.$router.go(n);
                }

            },
        }
    }
</script>

<style lang="scss" scoped>
    @import "../styles/variables";
.qrticket{
    background: $su-color-content-light;
    //border: 1px solid #00c4ab;
    box-shadow: $su-shadow;
    padding: 10px 25px;
    margin: 10px;
    display: inline-block;
    text-align: left;
    border-radius: 8px;
}

b{
    width: 100%;
    text-align: center;
    display: block;
    font-size: 1.5rem;
    margin-top: 8px;
}

    button {
        margin-top: 16px;
        display: block;
    }
</style>