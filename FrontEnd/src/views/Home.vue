<template>
    <div id="home">
        <div id="nav">
            <div id="su-top-bar">
                <img src="../assets/icons/logo2.svg">
                <p>ServeUp</p>
            </div>
            <router-link to="/home/orders">
                <img src="../assets/icons/orders.svg" alt="O">
                <p>Trenutna Naročila</p>
            </router-link>
            <router-link to="/home/history" append>
                <img src="../assets/icons/history.svg" alt="O">
                <p>Zgodovina Naročil</p>
            </router-link>
            <router-link to="/home/QRCode" append>
                <img src="../assets/icons/tables.svg" alt="O">
                <p>Mize</p>
            </router-link>
            <router-link to="/home/stats" append>
                <img src="../assets/icons/stats.svg" alt="O">
                <p>Statistika</p>
            </router-link>
            <!--<router-link to="/home/discounts" append>
                <img src="../assets/icons/discounts.svg" alt="O">
                <p>Popusti</p>
            </router-link>-->

            <router-link id="su-nav-settings" to="/" append @click="logOut">
                <img src="../assets/icons/settings.svg" alt="O">
                <p>Log out</p>
            </router-link>
        </div>
        <router-view></router-view>
    </div>
</template>

<script>

export default {
    name:"Home",
    data() {
        return {

        }
    },

    methods: {
        logOut() {
            this.$session.destroy();
        }
    },

    beforeCreate: function () {
            try{
                if (this.$session.exists()) {
                    console.log('obstaja');
                }
                else{
                    console.log('NE obstaja');
                    this.$router.push({ name: "login" });
                }
            }
            catch (e) {
                console.log("error before", e);
                this.$router.push({ name: "login" });
            }

    }
}
</script>

<style lang="scss">
    @import "../styles/variables";

    #home {
        width: 100%;
        height: 100%;
        display: flex;

        #nav {
            width: 15rem;
            min-width: 15rem !important;
            display: flex;
            flex-direction: column;
            background: $su-color-primary-dark;
            box-shadow: $su-shadow;
            z-index: 20;

            #su-top-bar {
                background: lighten($su-color-primary-dark, 5);
                margin-bottom: 8px;
                display: flex;
                justify-content: center;

                img {
                    margin: auto;
                    left: 2rem;
                    top: 0.5rem;
                    position: absolute;
                    width: 2rem;

                }

                p {
                    position: relative;
                    color: $su-color-content-light;
                    font-weight: bold;

                }
            }

            a {
                font-size: 0.9rem;
                text-decoration: none;
                color: $su-color-content-light;
                text-align: start;
                padding: 1rem;
                &.router-link-exact-active {
                    background: lighten($su-color-primary-dark, 5);
                    border-left: 4px solid $su-color-primary;
                }

                img {
                    position: absolute;
                    display: inline;
                    width: 1rem;
                }

                p {
                    position: relative;
                    margin-left: 2rem;
                    display: inline;
                    color: $su-color-content-light;
                }
            }

            #su-nav-settings {
                position: absolute;
                bottom: 1rem;
            }
        }
    }
</style>
