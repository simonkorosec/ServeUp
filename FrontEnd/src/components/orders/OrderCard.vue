<template>
    <div class="su-order-card-body" :class="highlighted=isHighlighted">
        <div class="su-order-card-header">
            <p class="su-order-card-owner"><slot name="ownerName"></slot></p>
            <p class="su-order-card-time">ob <slot name="arrivalTime"></slot></p>
            <p class="su-order-card-total"><slot name="priceTotal"></slot>€</p>
        </div>
        <div class="su-order-card-items">
            <slot name="orderItems"></slot>
        </div>
        <div class="su-order-card-footer">
            <button @click="changeStatusCard()">NASLEDNJI KORAK</button>
            <p><slot name="totalPrepTime"></slot>m</p>
        </div>
    </div>
</template>

<script>
import {EventBus} from "../../Events";

export default {
    name: "OrderCard",
    props: {
        orderId: Number
    },
    data() {
      return {
          isHighlighted: false,
      }
    },
    methods: {
        // Use the global event bus Event.js to emit
        // the card's status has been changed
        changeStatusCard() {
            EventBus.$emit('changeStatus', this.orderId);
        }
    }
};
</script>
<style lang="scss" scoped>
    @import "../../styles/variables";

    @keyframes flash-animation {
        0% { background: $su-color-content-light; }
        50%   { background: $su-color-secondary; }
        100%   {background: $su-color-content-light;}
    }

    div {
        display: flex;
        :not(.su-order-card-body) {

        }
    }

    .su-order-card-body {
        flex-direction: column;
        margin: 32px;
        background: $su-color-content-light;
        box-shadow: $su-shadow;
        border-radius: $su-border-radius-m;
        overflow: hidden;

        &:hover {
            @include su-mx-shadow-hover($su-shadow, $su-shadow-hover);
        }

        .su-order-card-header {
            font-size: 1.2rem;
            @include su-mx-layout-menu-last-end;
            @include su-mx-color-first-last-child(
                            $su-color-primary,
                            $su-color-primary);

        }

        .su-order-card-items{
            flex-direction: column;
            margin-left: 16px;
            margin-right: 16px;
            border-top: 2px solid $su-color-light-gray;
            border-bottom: 2px solid $su-color-light-gray;
        }

        .su-order-card-footer{
            @include su-mx-layout-menu-last-end;

            * {

            }

            :last-child {
                color: $su-color-primary;
                font-size: 1.2rem;
            }

            button {
                margin-top: 8px;
                margin-bottom: 8px;
                padding-left: 64px;
                padding-right: 64px;
                background: $su-color-secondary;
                color: $su-color-content-light;
                box-shadow: $su-shadow;
                border: none;
                border-radius: $su-border-radius-m;
                cursor: pointer;

                &:active {
                    background: $su-color-secondary-dark;
                }

                &:hover {
                    @include su-mx-shadow-hover($su-shadow, $su-shadow-hover);
                }
            }
        }
    }

    .suHereNew {
        border: 8px solid $su-color-secondary;
        border-radius: 16px;
        box-shadow: 0 0 16px $su-color-secondary;

    }

    .suHereMaking {
        border: 8px solid $su-color-secondary;
        border-radius: 16px;
        box-shadow: 0 0 16px $su-color-secondary;
    }

    .suHereReady {
        border: 8px solid $su-color-secondary;
        border-radius: 16px;
        box-shadow: 0 0 16px $su-color-secondary;
    }

    .highlighted {
        animation-name: flash-animation;
        animation-duration: 1s;
    }
</style>
