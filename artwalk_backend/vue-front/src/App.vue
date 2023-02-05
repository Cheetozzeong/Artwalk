<template>
  <b-container fluid id="app" class="d-flex">
    <div v-if="$store.state.isLogin">
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
                  <router-link :to="{ name: 'userBoard' }" class="tdn menulist" style="font-size: 1.5rem">User Board</router-link>
                </b-nav-item>
                <b-nav-item @click="hide" class="my-3">
                  <router-link :to="{ name: 'routeBoard' }" class="tdn menulist" style="font-size: 1.5rem">Route Board</router-link>
                </b-nav-item>
                <b-nav-item @click="hide" class="my-3">
                  <router-link :to="{ name: 'recordBoard' }" class="tdn menulist" style="font-size: 1.5rem">Record Board</router-link>
                </b-nav-item>
                <b-nav-item active @click="hide" class="my-3">
                  <router-link :to="{ name: 'login' }" class="tdn logout" style="font-size: 1.5rem">Logout</router-link>
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

.m-click {

}

.m-click:hover {
  cursor: pointer;
}

.f-size {
  font-size: 18px;
}

.x-scroll {
  overflow: auto;
  scroll-snap-type: x mandatory;
}

.x-scroll::-webkit-scrollbar {
  width: 3px;  /* 스크롤바의 너비 */
}

.x-scroll::-webkit-scrollbar-thumb {
  height: 20%; /* 스크롤바의 길이 */
  background: rgba(7, 167, 148, 0.44); /* 스크롤바의 색상 */
  border-radius: 10px;
}

.x-scroll::-webkit-scrollbar-track {
  background: rgb(255, 255, 255);  /*스크롤바 뒷 배경 색상*/
  border-radius: 10px;
}

</style>
