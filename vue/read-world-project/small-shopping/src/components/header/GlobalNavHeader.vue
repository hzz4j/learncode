<template>
  <!-- 导航栏 -->
  <nav class="menus-wrapper">
    <ul class="container">
      <li class="menu-item">
        <a href="javascript:void(0)">
          <i class="iconfont icon-user"></i>Q10Viking</a
        >
      </li>
      <li class="menu-item"><a href="javascript:void(0)">退出登录</a></li>
      <li class="menu-item"><a href="javascript:void(0)">会员中心</a></li>
      <li class="menu-item"><a href="javascript:void(0)">帮助中心</a></li>
      <li class="menu-item"><a href="javascript:void(0)">关于我们</a></li>
      <li class="menu-item">
        <a href="javascript:void(0)">
          <i class="iconfont icon-shouji"></i>手机版</a
        >
      </li>
    </ul>
  </nav>
</template>

<style scoped lang="scss">
// @import "../../assets/styles/_variables";
.menus-wrapper {
  background-color: $dark-color1;
  .container {
    height: 5.3rem;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .menu-item {
      padding: 0 1rem;
      font-size: 1.6rem;
      text-align: center;
      a {
        color: $gray-color;
        // @include colorText($theme-primary-color);
        .iconfont {
          font-size: 1.6rem;
          margin-right: 0.5rem;
        }
        &:hover {
          color: $theme-primary-color;
        }
      }

      ~ .menu-item {
        border-left: 1px solid #666;
      }
    }
  }
}
</style>
