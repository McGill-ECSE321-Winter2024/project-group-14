<!-- <template>
    <div>
        <div class="home-page">
          <!-- Footer -->
          <div class="footer"></div>
          <div class="frame-227">
            <div class="contacts">
              <div class="contact-us">Contact us</div>
              <div class="list">
                <div class="phone">+1 888-888-8888</div>
                <div class="spc-666-gmail-com">SPC666@gmail.com</div>
              </div>
            </div>
            <div class="social-media">
              <div class="item">
                <div class="instagram">Instagram</div>
              </div>
              <div class="item">
                <div class="whatsapp">Whatsapp</div>
              </div>
              <div class="item">
                <div class="telegram">Telegram</div>
              </div>
            </div>
          </div>
          <!-- Sign up and log in buttons -->
          <div class="button">
            <a href="/#/SignUp" class="sign-up" id="sign-up">Sign Up</a>
          </div>
          <div class="button2">
            <a href="/#/Login" class="log-in" id="log-in">Log in</a>
          </div>

          <!-- insert the picture -->
          <div class="picture-1">
            <img v-bind:src="require('@/assets/hp-pic1.png')" alt="home-page picture">
          </div>

  
          <!-- opening hour square -->
          <div class="square">
            <p>Monday    8:30 to 5:30</p>
            <p>Tuesday   8:30 to 5:30</p>
            <p>Wednesday 8:30 to 5:30</p>
            <p>Thursday  8:30 to 5:30</p>
            <p>Friday    8:30 to 5:30</p>
            <p>Saturday  8:30 to 5:30</p>
            <p>Sunday    8:30 to 5:30</p>
          </div>
          <!-- Navigation bar -->
          <div class="frame-1618871194">
            <div class="courses">Courses</div>
            <div class="schedule">Schedule</div>
            <div class="our-staff">Our Staff</div>
            <div class="contact">Contact</div>
          </div>
          <div class="group-266">
        <div class="ellipse-519"></div>
        <svg
          class="group-265"
          width="20"
          height="20"
          viewBox="0 0 20 20"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M17.7942 5.5L10 19L2.20577 5.5L17.7942 5.5Z"
            stroke="white"
          />
          <path d="M3 6L10 10.5L17.5 6" stroke="white" stroke-linecap="round" />
          <path d="M10 10.5V18.5" stroke="white" stroke-linecap="round" />
        </svg>
      </div>
      <div class="sport-center-web-app">Sport Center Web app</div>
    </div>
</div>
</template>

<style scoped>

.home-page * {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.home-page {
  background: linear-gradient(
    180deg,
    rgba(97, 237, 237, 1) 0%,
    rgba(153, 153, 153, 1) 100%
  );
  height: 1024px;
  position: relative;
  overflow: hidden;
}
.picture-1 {
  width: 50%;
  margin-top: 180px;
  margin-left: 30px;
}
.picture-1 img {
  width: 85%;
  height: auto;
}
.footer {
  background: #000000;
  height: 155px;
  position: absolute;
  right: 0px;
  left: 0px;
  bottom: 0px;
}
.frame-227 {
  display: flex;
  flex-direction: row;
  align-items: flex-end;
  justify-content: space-between;
  width: 684px;
  position: absolute;
  left: 732px;
  top: 916px;
}
.contacts {
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: flex-start;
  justify-content: flex-start;
  flex-shrink: 0;
  position: relative;
}
.contact-us {
  color: #ffffff;
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 10px;
  line-height: 140%;
  font-weight: 500;
  text-transform: uppercase;
  opacity: 0.6000000238418579;
  position: relative;
}
.list {
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: flex-start;
  justify-content: flex-start;
  flex-shrink: 0;
  position: relative;
}
.phone {
  color: #ffffff;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 14px;
  line-height: 140%;
  font-weight: 400;
  position: relative;
}
.spc-666-gmail-com {
  color: #ffffff;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 14px;
  line-height: 140%;
  font-weight: 400;
  position: relative;
}
.social-media {
  display: flex;
  flex-direction: row;
  gap: 8px;
  align-items: flex-start;
  justify-content: flex-start;
  flex-shrink: 0;
  position: relative;
}
.item {
  border-radius: 40px;
  border-style: solid;
  border-color: rgba(255, 255, 255, 0.24);
  border-width: 1px;
  padding: 6px 20px 6px 20px;
  display: flex;
  flex-direction: row;
  gap: 0px;
  align-items: flex-start;
  justify-content: flex-start;
  flex-shrink: 0;
  position: relative;
}
.instagram {
  color: #ffffff;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 12px;
  line-height: 130%;
  font-weight: 400;
  position: relative;
}
.whatsapp {
  color: #ffffff;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 12px;
  line-height: 130%;
  font-weight: 400;
  position: relative;
}
.telegram {
  color: #ffffff;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 12px;
  line-height: 130%;
  font-weight: 400;
  position: relative;
}
.button {
  border-radius: 10px;
  border-style: solid;
  border-color: #000000;
  border-width: 1px;
  padding: 10px 32px 10px 32px;
  display: flex;
  flex-direction: row;
  gap: 10px;
  align-items: center;
  justify-content: center;
  position: absolute;
  left: 1254px;
  top: 63px;
}
.sign-up {
  color: var(
    --milestone-webflow-html-website-templatewebflowio1440x761default-1-black,
    #000000
  );
  text-align: left;
  font-family: "Manrope-SemiBold", sans-serif;
  font-size: 16px;
  line-height: 24px;
  font-weight: 600;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.button2 {
  border-radius: 10px;
  border-style: solid;
  border-color: #000000;
  border-width: 1px;
  padding: 10px 32px 10px 32px;
  display: flex;
  flex-direction: row;
  gap: 10px;
  align-items: center;
  justify-content: center;
  position: absolute;
  left: 1128px;
  top: 63px;
}
.log-in {
  color: var(
    --milestone-webflow-html-website-templatewebflowio1440x761default-1-black,
    #000000
  );
  text-align: left;
  font-family: "Manrope-SemiBold", sans-serif;
  font-size: 16px;
  line-height: 24px;
  font-weight: 600;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.frame-1618871194 {
  display: flex;
  flex-direction: row;
  gap: 56px;
  align-items: flex-start;
  justify-content: flex-start;
  position: absolute;
  left: calc(50% - 183px);
  top: 68px;
}
.courses {
  color: #222222;
  text-align: left;
  font-family: "Manrope-SemiBold", sans-serif;
  font-size: 13px;
  line-height: 24px;
  font-weight: 600;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.schedule {
  color: #222222;
  text-align: left;
  font-family: "Manrope-SemiBold", sans-serif;
  font-size: 13px;
  line-height: 24px;
  font-weight: 600;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.our-staff {
  color: #222222;
  text-align: left;
  font-family: "Manrope-SemiBold", sans-serif;
  font-size: 13px;
  line-height: 24px;
  font-weight: 600;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.contact {
  color: #222222;
  text-align: left;
  font-family: "Manrope-SemiBold", sans-serif;
  font-size: 13px;
  line-height: 24px;
  font-weight: 600;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.group-267 {
  position: absolute;
  inset: 0;
}
.group-266 {
  width: 28px;
  height: 28px;
  position: static;
}
.ellipse-519 {
  background: #0e1125;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  position: absolute;
  left: 48px;
  top: 58px;
}
.group-265 {
  width: 20px;
  height: 20px;
  position: absolute;
  left: 52px;
  top: 62px;
  overflow: visible;
}
.sport-center-web-app {
  color: #0e1125;
  text-align: left;
  font-family: "Ramabhadra-Regular", sans-serif;
  font-size: 18px;
  font-weight: 400;
  position: absolute;
  left: 84px;
  top: 55px;
}
.square {
  width: 400px; 
  height: 400px; 
  border: 2px solid black; 
  display: flex; 
  justify-content: center; 
  align-items: center; 
  flex-direction: column; 
  padding: 10px; 
  text-align: center; 
  position: absolute;
  top:250px;
  right:70px;
  font-size: 2vw;
}
</style> -->