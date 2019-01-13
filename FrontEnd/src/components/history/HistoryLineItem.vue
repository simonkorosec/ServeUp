<template>
    <div class="su-history-line-item-wrapper">
        <div class="su-history-line-item" @click="highlightOrder()"></div>
    </div>
</template>

<script>
    import {EventBus} from "../../Events";

    export default {
        name: "HistoryLineItem",

        props: {
            orderId: Number
        },

        methods: {
            highlightOrder() {
                console.log(this.orderId);
                EventBus.$emit('highlightHis', this.orderId);
            },
        }
    }
</script>

<style lang="scss" scoped>
    @import "../../styles/variables";

    .su-history-line-item-wrapper {

        .su-history-line-item {
            background: $su-color-background;
            width: 100%;
            height: 100%;
            border-radius: 0.2rem;

            &:hover {
                @include su-mx-shadow-hover($su-shadow, $su-shadow-hover);
            }
        }
    }
</style>