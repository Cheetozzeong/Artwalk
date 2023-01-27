<template>
  <b-container fluid id="app" class="d-flex">
    <div>
      <b-button v-b-toggle.sidebar-no-header style="font-size: 2rem" variant="light" class="mt-3 justify-content-start">
        <b-icon icon="list" class="pb-2"></b-icon>
      </b-button>
    </div>
    <b-sidebar id="sidebar-no-header" aria-labelledby="sidebar-no-header-title" no-header shadow>
      <template #default="{ hide }">
        <div class="p-3 d-flex flex-column justify-content-between" style="display: block; height: 100%">
          <div>
            <router-link to="/admin/main" id="sidebar-no-header-title" class="mb-4 tdn menulist p-3" style="font-size: 2rem">ArtWalk</router-link>
            <hr>
            <nav class="my-3">
              <b-nav vertical>
                <b-nav-item @click="hide" class="my-3">
                  <router-link to="/admin/board/user" class="tdn menulist" style="font-size: 1.5rem">User Board</router-link>
                </b-nav-item>
                <b-nav-item @click="hide" class="my-3">
                  <router-link to="/admin/board/route" class="tdn menulist" style="font-size: 1.5rem">Route Board</router-link>
                </b-nav-item>
                <b-nav-item @click="hide" class="my-3">
                  <router-link to="/admin/board/record" class="tdn menulist" style="font-size: 1.5rem">Record Board</router-link>
                </b-nav-item>
                <b-nav-item active @click="hide" class="my-3">
                  <router-link to="/" class="tdn logout" style="font-size: 1.5rem">Logout</router-link>
                </b-nav-item>
              </b-nav>
            </nav>
          </div>
          <div class="d-flex justify-content-end">
            <b-button @click="hide" style="font-size: 2rem" variant="light">
              <b-icon icon="chevron-double-left" class="pb-2"></b-icon>
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
  font-family: 'Noto Sans KR', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.tdn {
  text-decoration-line: none;
}

.menulist {
  color: #3c3c3c;
}

.logout {
  color: #3c3c3c;
}

.menulist:hover {
  color: #07A794;
}

.logout:hover {
  color: #D32F2F;
}

.maincolor {
  color: #07A794;
}

.maincolor:hover {
  color: #FF9691;
}

</style>
