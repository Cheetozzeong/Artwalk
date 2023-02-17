<template>
  <b-container>

    <div class="d-flex justify-content-center align-content-center mt-5 mb-1">
      <h2>{{ userInfo.nickname }}'s Info </h2>
    </div>
    <br>

    <!--  유저 정보  -->
    <b-row class="d-flex">
      <b-col class="col-5">
        <h3>Profile Image</h3>
        <b-img width="250vh" height="250vh" rounded thumbnail fluid :src="imageUrl"></b-img>
      </b-col>
      <b-col class="col-2 f-size">
        <div class="my-3">User Id</div>
        <div class="my-3">User Type</div>
        <div class="my-3">Level</div>
        <div class="my-3">Exp</div>
      </b-col>
      <b-col class="col-5 f-size">
        <div class="fw-bold my-3">{{ userInfo.userId }}</div>
        <div class="fw-bold my-3">{{ formattedUserType }}</div>
        <div class="fw-bold my-3">{{ userInfo.level }}</div>
        <div class="fw-bold my-3">{{ userInfo.exp }}</div>
      </b-col>
    </b-row>

    <!--  유저의 루트 리스트  -->
    <div class="my-4">
      <h5>
        {{ userInfo.nickname }}'s Route
      </h5>
      <b-row class="x-scroll">
        <RouteItem
            v-for="userRoute in userRoutes"
            :key="userRoute.routeId"
            :route="userRoute"
        />
      </b-row>
    </div>


    <!--  유저의 레코드 리스트  -->
    <div class="my-4">
      <h5>
        {{ userInfo.nickname }}'s Record
      </h5>
      <b-row class="x-scroll">
        <RecordItem
            v-for="userRecord in userRecords"
            :key="userRecord.recordId"
            :record="userRecord"
        />
      </b-row>
    </div>

  </b-container>
</template>

<script>
import Send from "@/utils/Send";
import RouteItem from "@/components/RouteItem.vue";
import RecordItem from "@/components/RecordItem.vue";

export default {
  name: "UserDetailView.vue",
  components: {RecordItem, RouteItem},
  data() {
    return {
      userInfo: null,
      userRoutes: null,
      userRecords: null,
      formattedUserType: null,
      imageUrl: null,
    }
  },
  methods: {
    getUserDetail() {
      return Send({
        method: 'get',
        url: '/user',
        params: {
          userId: this.$route.params.userId
        }
      })
          .then((res) => {
            this.userInfo = res.data.user
            this.formattedUserType = this.userInfo ? 'Social':'App'
          })
          .catch((err) => {
            this.err = err
            // console.log(err)
          })
    },
    getUserRoute() {
      return Send({
        method: 'get',
        url: `/route/list/${this.$route.params.userId}`,
      })
          .then((res) => {
            // console.log(res)
            this.userRoutes = res.data.routes
          })
          .catch((err) => {
            this.err = err
            // console.log(err)
          })
    },
    getUserRecord() {
      return Send({
        method: 'get',
        url: `/record/list/${this.$route.params.userId}`,
      })
          .then((res) => {
            // console.log(res)
            this.userRecords = res.data.records
          })
          .catch((err) => {
            this.err = err
            // console.log(err)
          })
    },
    getProfileImage() {
      const url = `/user/info/profile?userId=${this.$route.params.userId}`

      const options = {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'accessToken': `Bearer ${this.$store.state.token}`
        }
      }

      fetch(url, options)
          .then(res => res.blob())
          .then(blob => {
            this.imageUrl = URL.createObjectURL(blob)
          })
    },
  },
  created() {
    this.getUserDetail()
    this.getUserRoute()
    this.getUserRecord()
    this.getProfileImage()
  }
}
</script>

<style scoped>

</style>