<template>
  <div class="footer">
    <!-- section-1 start -->
    <section class="section-1 container">
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
    <section class="section-2">
      <div class="line-1">
        <div class="item">
          <i class="iconfont icon-emptybox"></i>
          <span class="name">价格亲民</span>
        </div>
        <div class="item">
          <i class="iconfont icon-wuliu"></i>
          <span class="name">物流快捷</span>
        </div>
        <div class="item">
          <i class="iconfont icon-dianzan"></i>
          <span class="name">品质新鲜</span>
        </div>
      </div>

      <div class="line-2">
        <ul class="sub-titles">
          <li class="title">关于我们</li>
          <li class="title">帮助中心</li>
          <li class="title">售后服务</li>
          <li class="title">配送与检验</li>
          <li class="title">商务合作</li>
          <li class="title">搜索推荐</li>
          <li class="title">友情链接</li>
        </ul>
        <p class="copyright">CopyRight &copy;<span class="name">静默</span></p>
      </div>
    </section>
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

.section-2 {
  color: #fff;
  background-color: #000;
  .line-1 {
    display: flex;
    justify-content: space-evenly;
    padding: 5rem 0;
    border-bottom: 1px solid #434343;
    .item {
      display: flex;
      align-items: center;
      i {
        margin-right: 1rem;
        padding: 1rem;
        border-radius: 50%;
        border: 2px solid #fff;
        border-right-width: 0;
        font-size: 4rem;
      }
      font-size: 2rem;
    }
  }

  .line-2 {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 2rem;
    color: $gray-light-color;
    .sub-titles {
      display: flex;
      .title {
        margin: 0.2rem 0 0.5rem 0;
        padding: 0 1rem;

        & ~ .title {
          border-left: 1px solid #fff;
        }
      }
    }
    .copyright {
      .name {
        margin-left: 0.5rem;
        color: $theme-primary-color;
      }
    }
  }
}
</style>
