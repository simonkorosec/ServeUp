<template>
    <div class="su-history-time-section">
        <div class="su-history-time-section-divider" @click="toggleContent">
            <p><slot name="timeLabel"></slot></p>
        </div>
        <div class="su-history-section-container" v-if="visible">
            <slot name="historyCards"></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HistorySection",
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

    .su-history-time-section {
        width: 100%;
        display: flex;
        flex-direction: column;

        .su-history-time-section-divider {
            position: sticky;
            cursor: pointer;
            background: $su-color-content-light;
            box-shadow: $su-shadow;
            z-index: 10;
            width: 100%;
            top: 0;
            left: 0;
            p {
                font-weight: bold;
                color: $su-color-dark-gray;
            }
        }

        .su-history-section-container {
            display: flex;
            flex-wrap: wrap;
            width: 100%;
            height: 100%;
        }
    }
</style>