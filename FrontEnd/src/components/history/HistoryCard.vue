<template>
    <div class="su-card-wrapper">
        <div class="su-history-card-body" :class="highlightedH=isHighlightedH" :id="'su-card-h-' + orderId">
            <div class="su-history-card-header">
                <p class="su-history-card-owner"><slot name="ownerName"></slot></p>
                <p class="su-history-card-time">Ob <slot name="arrivalTime"></slot></p>
                <p class="su-history-card-total"><slot name="priceTotal"></slot>€</p>
            </div>
            <div class="su-history-card-items">
                <slot name="historyItems"></slot>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HistoryCard",
        props: {
            orderId: Number
        },
        data() {
            return {
                isHighlightedH: false,
            }
        },
    }
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
        :not(.su-history-card-body) {

        }
    }

    .su-card-wrapper {

        .su-history-card-body {
            box-sizing: border-box;
            margin: 0;
            width: 100%;
            height: 100%;
            flex-direction: column;
            background: $su-color-content-light;
            box-shadow: $su-shadow;
            border-radius: $su-border-radius-m;
            overflow: hidden;

            &:hover {
                @include su-mx-shadow-hover($su-shadow, $su-shadow-hover);
            }

            .su-history-card-header {
                font-size: 1.2rem;
                @include su-mx-layout-menu-last-end;
                @include su-mx-color-first-last-child($su-color-primary, $su-color-primary);

            }

            .su-history-card-items{
                flex-direction: column;
                margin-left: 16px;
                margin-right: 16px;
                border-top: 2px solid $su-color-light-gray;
                border-bottom: none;
            }
        }
    }

    .highlightedH {
        animation-name: flash-animation;
        animation-duration: 1s;
    }
</style>