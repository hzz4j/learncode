<template>
  <div class="footer container">
    <!-- section-1 start -->
    <section class="section-1">
      <dl>
        <dt>客服服务</dt>
        <div class="content-wrapper">
          <dd>
            <i class="iconfont icon-kefufill"></i>
            <span class="description">在线客服</span>
          </dd>
          <dd>
            <i class="iconfont icon-questionfill"></i>
            <span class="description">问题反馈</span>
          </dd>
        </div>
      </dl>

      <dl>
        <dt>关注我们</dt>
        <div class="content-wrapper">
          <dd>
            <i class="iconfont icon-weixin"></i
            ><span class="description">公众号</span>
          </dd>
          <dd>
            <i class="iconfont icon-xinlangweibo"></i
            ><span class="description">微博</span>
          </dd>
        </div>
      </dl>

      <dl>
        <dt>下载APP</dt>
        <div class="content-wrapper">
          <dd><img src="/images/qrcode.jpg" alt="" /></dd>
          <dd class="download">
            <p class="desc-wrapper">
              <span class="description">扫描二维码</span>
              <span class="description">立马下载APP</span>
            </p>
            <button>下载页面</button>
          </dd>
        </div>
      </dl>

      <dl>
        <dt>服务热线</dt>
        <div class="content-wrapper">
          <dd class="hotline">
            400-0000-000 <small>周一至周日 8:00-18:00</small>
          </dd>
        </div>
      </dl>
    </section>
    <!-- section-1 end -->

    <!-- section-2 start -->
    <section class="section-2"></section>
    <!-- section-2 end -->
  </div>
</template>

<style lang="scss" scoped>
.section-1 {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  padding: 5rem 0;
  dl {
    color: $gray-light-color;

    & ~ dl {
      border-left: 1px solid #ededed;
    }

    .content-wrapper {
      display: flex;
      justify-content: center;
    }

    dt {
      text-align: center;
      font-size: 1.8rem;
    }

    dd {
      width: $dd-size;
      height: $dd-size;
      margin: 3.2rem 1.2rem 0 0;
      border: 1px solid #ededed;

      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-evenly;

      &.download {
        justify-content: space-between;
        text-align: center;
        border: none;
        .desc-wrapper {
          .description {
            margin-top: 0.8rem;
            display: block;
          }
        }

        button {
          width: 100%;
          border: none;
          padding: 0.5rem;
          font-size: 1.4rem;
          cursor: pointer;
          color: #fff;
          background-color: $theme-primary-color;
          border-radius: 0.2rem;
        }
      }

      &:last-child {
        margin-right: 0;
      }

      &.hotline {
        justify-content: flex-end;
        width: calc(#{$dd-size} * 2);
        border: none;
        font-size: 2rem;
        color: $gray-deep-color;

        small {
          margin: 0.5rem 0 1rem 0;
          color: $gray-light-color;
          font-size: $text-size;
        }
      }

      i {
        font-size: 3.6rem;
        color: $gray-deep-color;

        &:hover {
          color: $theme-primary-color;
        }
      }

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      .description {
        font-size: $text-size;
      }
    }
  }
}
</style>
