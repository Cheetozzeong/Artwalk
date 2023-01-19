<template>
  <b-container fluid id="app" class="d-flex">
    <div>
      <b-button v-b-toggle.sidebar-no-header style="font-size: 1.5rem" variant="light" class="mt-3 justify-content-start">
        <b-icon icon="list"></b-icon>
      </b-button>
    </div>
    <b-sidebar id="sidebar-no-header" aria-labelledby="sidebar-no-header-title" no-header shadow>
      <template #default="{ hide }">
        <div class="p-3 d-flex flex-column justify-content-between">
          <div>
            <h2 id="sidebar-no-header-title">ArtWalk</h2>
            <hr>
            <nav class="my-3">
              <b-nav vertical>
                <b-nav-item active @click="hide" class="my-3">
                  <router-link to="/admin/login" class="tdn menulist">Login</router-link>
                </b-nav-item>
                <b-nav-item @click="hide" class="my-3">
                  <router-link to="/admin/board/user" class="tdn menulist">User Board</router-link>
                </b-nav-item>
                <b-nav-item @click="hide" class="my-3">
                  <router-link to="/admin/board/record" class="tdn menulist">Record Board</router-link>
                </b-nav-item>
              </b-nav>
            </nav>
          </div>
          <div class="d-flex justify-content-end">
            <b-button @click="hide" style="font-size: 1.5rem" variant="light">
              <b-icon icon="chevron-double-left"></b-icon>
            </b-button>
          </div>
        </div>
      </template>
    </b-sidebar>
    <router-view/>
  </b-container>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #07A794;
}

.tdn {
  text-decoration-line: none;
}

.menulist {
  color: #3c3c3c;
}

.menulist:hover {
  color: #07A794;
}

.maincolor {
  color: #07A794;
}

</style>
