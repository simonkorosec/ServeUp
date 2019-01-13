<template>
    <div id="su-stats">
        <p class="su-stats-upgrade">Prosimo nadgradite na <span>ServeUp</span> Plus</p>
    </div>
</template>

<script>
    export default {
        name: "Stats"
    }
</script>

<style lang="scss" scoped>
    @import "../styles/variables";
    #su-stats {
        height: 100%;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .su-stats-upgrade {
        font-size: 5rem;
        color: $su-color-content-light;
        background: $su-color-primary-dark;
        padding: 2rem;
        border-radius: 16px;
        box-shadow: $su-shadow;

        span {
            color: $su-color-primary;
        }
    }
</style>