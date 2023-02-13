<template>
  <b-container fluid id="app" class="d-flex">
    <div v-if="$store.state.isLogin && !$store.state.isError">
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
                  <router-link @click.native.prevent="$store.dispatch('logout')" :to="{ name: 'main' }" class="tdn logout" style="font-size: 1.5rem">Logout</router-link>
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
  font-size: 1.1rem;
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

.custom-pagination {
  --bs-pagination-padding-x: 0.75rem;
  --bs-pagination-padding-y: 0.375rem;
  --bs-pagination-font-size: 1rem;
  --bs-pagination-color: #07a794;
  --bs-pagination-bg: #fff;
  --bs-pagination-border-width: 1px;
  --bs-pagination-border-color: #dee2e6;
  --bs-pagination-border-radius: 0.375rem;
  --bs-pagination-hover-color: #07a794;
  --bs-pagination-hover-bg: #e9ecef;
  --bs-pagination-hover-border-color: #dee2e6;
  --bs-pagination-focus-color: #07a794;
  --bs-pagination-focus-bg: #e9ecef;
  --bs-pagination-focus-box-shadow: 0 0 0 0.25rem rgba(7, 167, 148, 0.22);
  --bs-pagination-active-color: #fff;
  --bs-pagination-active-bg: #07a794;
  --bs-pagination-active-border-color: #07a794;
  --bs-pagination-disabled-color: #6c757d;
  --bs-pagination-disabled-bg: #fff;
  --bs-pagination-disabled-border-color: #dee2e6;
  display: flex;
  padding-left: 0;
  list-style: none;
  margin-top: 2rem;
}

</style>
