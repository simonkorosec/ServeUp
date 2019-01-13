<template>
    <div class="su-order-item-body">
        <p><slot name="amount"></slot>x</p>
        <p><slot name="foodName"></slot></p>
        <p><slot name="prepTime"></slot>m</p>
    </div>
</template>

<script>
export default {
    name: "OrderCardItem",
}
</script>

<style lang="scss" scoped>
    @import "../../styles/variables";

    .su-order-item-body {
        display: flex;
        margin: 0;
        @include su-mx-layout-menu-last-end;
        @include su-mx-color-first-last-child($su-color-dark-gray, $su-color-primary);
    }
</style>