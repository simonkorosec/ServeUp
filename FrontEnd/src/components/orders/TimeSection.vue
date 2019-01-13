<template>
    <div class="su-time-section">
        <div class="su-time-section-divider" @click="toggleContent">
            <p><slot name="timeLabel"></slot></p>
        </div>
        <div class="su-time-section-container" v-if="visible">
            <div class="su-time-section-new" >
                <slot name="sectionNew"></slot>
            </div>
            <div class="su-time-section-making">
                <slot name="sectionMaking"></slot>
            </div>
            <div class="su-time-section-ready">
                <slot name="sectionReady"></slot>
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        name: "TimeSection",

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

    .su-time-section {
        width: 100%;
        display: flex;
        flex-direction: column;

        .su-time-section-divider {
            position: sticky;
            background: $su-color-content-light;
            box-shadow: $su-shadow;
            z-index: 10;
            width: 100%;
            top: 0;
            left: 0;
            cursor: pointer;
            p {
                font-weight: bold;
                color: $su-color-dark-gray;
            }
        }

        .su-time-section-container {
            display: flex;
            align-content: stretch;
            align-items: stretch;
            width: 100%;
            height: 100%;

            div {
                flex: 1;
            }

            .su-time-section-new {
                background: $su-color-red-light;
            }

            .su-time-section-making {
                background: $su-color-yellow-light;
            }

            .su-time-section-ready {
                background: $su-color-green-light;
            }
        }
    }
</style>