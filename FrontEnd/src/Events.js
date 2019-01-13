import Vue from 'vue';
// Global event bus used for emitting events across all components.
export const EventBus = new Vue();
export const serverUrl = 'https://serveup-backend.herokuapp.com/api/';