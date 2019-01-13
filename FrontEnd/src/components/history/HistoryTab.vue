<template>
    <div class="su-history-tab">
        <slot name="historyTimeSections"></slot>
    </div>
</template>

<script>
    export default {
        name: "HistoryTab"
    }
</script>

<style lang="scss" scoped>
    .su-history-tab {
        width: 100%;
        height: 100%;
        overflow-y: auto;
        overflow-x: hidden;
    }
</style>