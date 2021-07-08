<template>

<v-data-table
    :headers="headers"
    :items="rentBicycleView"
    :items-per-page="5"
    class="elevation-1"
  ></v-data-table>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'RentBicycleView',
    props: {
      value: Object,
      editMode: Boolean,
      isNew: Boolean
    },
    data: () => ({
        headers: [
            { text: "ticketId", value: "ticketId" },
            { text: "ticketStatus", value: "ticketStatus" },
            { text: "ticketType", value: "ticketType" },
            { text: "buyerPhoneNum", value: "buyerPhoneNum" },
            { text: "paymentId", value: "paymentId" },
            { text: "paymentStatus", value: "paymentStatus" },
            { text: "addPaymentYn", value: "addPaymentYn" },
            { text: "bicycleId", value: "bicycleId" },
            { text: "bicycleStatus", value: "bicycleStatus" },
            { text: "usingTime", value: "usingTime" },
        ],
        rentBicycleView : [],
    }),
    async created() {
      var temp = await axios.get(axios.backend + '/rentbicycleviews')

      this.rentBicycleView = temp.data._embedded.rentbicycleviews;

    },
    methods: {
    }
  }
</script>

