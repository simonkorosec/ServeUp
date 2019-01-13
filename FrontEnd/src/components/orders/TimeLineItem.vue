
<template>
    <div class="su-time-line-item" @click="highlightOrder()"></div>
</template>

<script>
    import {EventBus} from "../../Events";

    export default {
        name: "TimeLineItem",
        props: {
            orderId: Number
        },
        methods: {
            highlightOrder() {
                console.log(this.orderId);
                EventBus.$emit('highlight', this.orderId);
            },
        }
    }
</script>

<style lang="scss" scoped>
    @import "../../styles/variables";

    .su-time-line-item {
        display: flex;
        height: 2.5rem;
        border-radius: 0.2rem;
        margin: 16px 8px;
        cursor: pointer;

        &:hover {
            @include su-mx-shadow-hover($su-shadow, $su-shadow-hover);
        }
    }

    .su-time-line-item-new {
        background: $su-color-red;
    }

    .su-time-line-item-making {
        background: $su-color-yellow;
    }

    .su-time-line-item-ready {
        background: $su-color-green;
    }

    .suHere {
        border: 0.5rem solid $su-color-secondary;
        border-radius: 0.2rem;
    }
</style>