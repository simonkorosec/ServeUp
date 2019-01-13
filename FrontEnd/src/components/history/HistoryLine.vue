<template>
    <div class="su-history-line">
        <div class="su-history-line-divider" @click="toggleContent">
            <p><slot name="timeLabel"></slot></p>
        </div>
        <div class="su-history-line-container" v-if="visible">
            <slot name="lineItems"></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HistoryLine",
        data() {
            return {
                visible: true,
            }
        },
        methods: {
            // Collapses or expands the time slot upon button click
            toggleContent() {
                this.visible = !this.visible
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import "../../styles/variables";

    .su-history-line {
        width: 100%;
        display: flex;
        flex-direction: column;

        .su-history-line-divider {
            position: sticky;
            cursor: pointer;
            background: $su-color-background;
            box-shadow: $su-shadow;
            z-index: 10;
            width: 100%;
            min-width: 9rem;
            top: 0;
            left: 0;
            p {
                margin: 8px;
                font-weight: bold;
                color: $su-color-primary-dark;
            }
        }

        .su-history-line-container {
            display: flex;
            flex-wrap: wrap;
            align-content: stretch;
            align-items: stretch;
            width: 100%;
            height: 100%;
        }
    }
</style>