<template>
    <div class="login">
        <form>
            <img id="su-logo" src="../assets/icons/logo2.svg">
            <label class="company-name">ServeUp</label>
            <input type="text" name="email" v-model="email" placeholder="Email"/><br>
            <input type="password" name="password" v-model="password" placeholder="Password"/><br>
            <p id="error">{{error}}</p>

            <p class="forgot_pass">Forgot password?</p><br>
            <button type="button" v-on:click="login()">Log in</button><br><br>

            <label>Don't have an account?</label><br>
            <router-link to="/sing_up" class="link">Sign up</router-link>
        </form>
    </div>
</template>
<script>
    import axios from 'axios';
    import {serverUrl} from "../Events";

    export default {
        name: 'Login',
        data() {
            return {
                email: "",
                password: "",
                error:""
            }
        },
        methods: {
            login:function(){
                if(this.email === "" || this.password === ""){
                    this.error="Uporabnisko ime in geslo ne smeta biti prazna.";
                }
                else{
                    axios.post(serverUrl + 'admin_user/login/', {
                        email: this.email,
                        password: this.password,
                        format: 'json',

                    }).then(response => {
                        console.log(response.data);
                        console.log("Uporabnik obstaja");
                        this.$router.push({ path: "/home/orders" });
                        this.$session.start();
                        this.$session.set('restaurantId', response.data.id_restavracija);
                        console.log("Session", this.$session.getAll());
                    }).catch(error => {
                        console.log(error);
                        this.error=error.response.data.description;
                        console.log("Uporabnik ne obstaja");
                    });

                }

            }
        }
    }
</script>
<style lang="scss" scoped>
    @import "../styles/variables";

    .login {
        height: 100%;
        width: 30rem;
        padding-bottom: 5rem;
    }

    #error{
        color:rgb(249, 102, 102);
        font-size: 12px;
        text-align: left;
        margin-left: 6px;
        margin-bottom: 0px;
        margin-top: 0px;
    }

    .company-name {
        font-size: 2rem;
        color: $su-color-primary;
        margin-top: 1rem;
        margin-bottom: 2rem;
        display: block;
    }

    #su-logo {
        height: 10rem;
        display: block;
        margin: auto;
        position: relative;
    }
</style>