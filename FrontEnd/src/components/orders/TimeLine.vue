<template>
    <div class="su-time-line">
        <div class="su-time-line-divider" @click="toggleContent">
            <p><slot name="timeLabel"></slot></p>
        </div>
        <div class="su-time-line-container" v-if="visible">
            <div class="su-time-line-new" >
                <slot name="sectionNew"></slot>
            </div>
            <div class="su-time-line-making">
                <slot name="sectionMaking"></slot>
            </div>
            <div class="su-time-line-ready">
                <slot name="sectionReady"></slot>
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        name: "TimeLine",
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

    .su-time-line {
        width: 100%;
        display: flex;
        flex-direction: column;

        .su-time-line-divider {
            cursor: pointer;
            position: sticky;
            background: $su-color-content-light;
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

        .su-time-line-container {
            display: flex;
            align-content: stretch;
            align-items: stretch;
            width: 100%;
            height: 100%;

            > div {
                width: 4rem;
                flex: 1 0;
            }


            .su-time-line-new {
                background: $su-color-red-light;
            }

            .su-time-line-making {
                background: $su-color-yellow-light;
            }

            .su-time-line-ready {
                background: $su-color-green-light;
            }
        }
    }
</style>