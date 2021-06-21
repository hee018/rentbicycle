
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import TicketManager from "./components/TicketManager"

import PaymentManager from "./components/PaymentManager"

import BicycleManager from "./components/bicycleManager"

import MessageManager from "./components/MessageManager"


import RentBicycleView from "./components/RentBicycleView"
export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/Ticket',
                name: 'TicketManager',
                component: TicketManager
            },

            {
                path: '/Payment',
                name: 'PaymentManager',
                component: PaymentManager
            },

            {
                path: '/bicycle',
                name: 'bicycleManager',
                component: bicycleManager
            },

            {
                path: '/Message',
                name: 'MessageManager',
                component: MessageManager
            },


            {
                path: '/RentBicycleView',
                name: 'RentBicycleView',
                component: RentBicycleView
            },


    ]
})
