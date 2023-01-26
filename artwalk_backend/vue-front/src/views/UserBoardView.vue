<template>
  <b-container>
    <div class="d-flex justify-content-center align-content-center mt-5 mb-1">
      <h1>User Board</h1>
    </div>
    <br>
    <div>
      <UserItem
          v-for="user in allUsers"
          :key="user.id"
          :user="user"
      />
    </div>
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
  }
}
</script>

<style scoped>

</style>