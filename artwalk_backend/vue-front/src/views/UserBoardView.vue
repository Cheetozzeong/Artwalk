<template>
  <b-container>
    <div class="d-flex justify-content-center align-content-center mt-5 mb-1">
      <h1>User Board</h1>
    </div>
    <br>
    <div>
      <!--  게시판  -->
      <b-table :fields="fields" :items="allUsers" sticky-header responsive>
        <template #cell(nickname)="data">
          <router-link :to="{ name: 'userDetail', params: { userId: data.item.userId } }" class="tdn maincolor">
            {{ data.item.nickname }}
          </router-link>
        </template>
        <template #cell(profile)="data">
          <img :src="data.item.profile">
        </template>
      </b-table>

    </div>
    <UserItem/>
  </b-container>
</template>

<script>
import UserItem from "@/components/UserItem.vue";
export default {
  name: "UserBoardView.vue",
  components: {
    UserItem
  },
  computed: {
    allUsers() {
      return this.$store.state.user
    },
  },
  data() {
    return {
      fields: [
        {
          key: 'userId',
          label: 'User Id'
        },
        {
          key: 'nickname',
          label: 'Nickname',
        },
        {
          key: 'profile',
          label: 'Profile Image'
        },
        {
          key: 'level',
          label: 'Level',
        },
        {
          key: 'exp',
          label: 'Exp',
        },
        {
          key: 'userRouteCount',
          label: "User's Route",
        },
        {
          key: 'userRecordCount',
          label: "User's Record",
        },
      ]
    }
  },
}
</script>

<style scoped>
</style>