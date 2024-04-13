<template>
  <div>
    <div class="sign-up-page">
      <div class="Sign-up">
        <!-- Enter you username here  -->
        <div class="username">
          <input type="text" class="text-field" v-model="username" placeholder="" />
          <div class="user-name2">Username</div>
          <!-- title for textbox -->
        </div>

        <!-- Enter you email here  -->
        <div class="email">
          <input type="text" class="text-field2" v-model="email" placeholder="" />
          <div class="email2">Email</div>
        </div>

        <!-- Enter you password here  -->
        <div class="password">
          <input type="password" class="text-field3" v-model="password" placeholder="" />
          <div class="password2">Password</div>
        </div>

        <!-- Enter you password to confirm here  -->
        <div class="confirm-password">
          <input
            type="password"
            class="text-field4"
            v-model="confirmPassword"
            placeholder=""
          />
          <div class="confirm-password2">Confirm Password</div>
        </div>

        <router-link to="/Login" class="toLogin"> Already a member? Log in
            <!-- <a href="/#/SignUp" class="signup-button" id="signup">Sign up</a> -->
        </router-link>
        <!-- Check if password are same  -->
        <!-- <div v-if="!passwordsMatch"></div> -->

        <!-- Enter you password Account Type here  -->
        <!-- <div class="enter-accounttype">
          <input type="text" class="text-field5" v-model="accounttype" placeholder="" />
          <div class="enter-accounttype2">Enter account type(Enter O,I,C)</div>
        </div> -->

        <!-- Error Sign up -->
        <div class="error-signup" v-if="errorSignUp">
          <h5 style="color: red; padding-top: 20px">
            Error: {{ errorSignUp }}
          </h5>
        </div>

        <!-- Successful sign up  -->
        <div class="succ-signup" v-if="successSignUp">
          <h5 style="color: green; padding-top: 20px">
            {{ successSignUp }}
          </h5>
        </div>

        <!-- Make "Sign Up" button  -->
        <div class="button-primary" v-bind:disabled="!email || !password || !confirmPassword || !username" @click="signup(email, password, confirmPassword, username)">
            Sign Up
        </div>
      </div>

      <div class="welcome">Welcome</div>

      <!-- Make "HomePage" button  -->
      <div class="purple-square">
        <div class="HomePage-buttons">
          <a href="/#/Home" class="home-page-button">
            <img
              class="home-page"
              :src="require('@/assets/home-page0.png')"
              alt="Home Page Icon"
            />
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./js/SignupPage.js"></script>

<style scoped>
a,
button,
input,
select,
h1,
h2,
h3,
h4,
h5,
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  border: none;
  text-decoration: none;
  appearance: none;
  background: none;

  -webkit-font-smoothing: antialiased;
}

.toLogin{
  position: absolute;
  top: 442px;
  font-size: 15px;
  right: 50px;
}
.toLogin:hover{
  text-decoration: underline;
}

.sign-up-page * {
  box-sizing: border-box;
}
.sign-up-page {
  background: linear-gradient(to right, #cccccc, #003366);
  height: 1024px;
  position: relative;
  overflow: hidden;
}
/* big white box */
.Sign-up {
  background: var(--white, #ffffff);
  border-radius: 12px;
  padding: 70px 40px 70px 40px;
  display: flex;
  flex-direction: column;
  gap: 31px;
  align-items: center;
  justify-content: flex-start;
  /* width: 502px; */
  /* height: 800px; */
  position: absolute;
  left: 34%;
  top: 292px;
}
.username {
  flex-shrink: 0;
  width: 360px;
  height: 68px;
  position: static;
}
.text-field {
  border-radius: 10px;
  border-style: solid;
  border-color: var(--black, #000000);
  border-width: 1px;
  width: 360px;
  height: 45px;
  position: absolute;
  left: 47px;
  top: 93px;
  font-size: 30px;
  padding-left: 10px;
}
.user-name2 {
  color: var(--light-grey, #979899);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: absolute;
  left: 53px;
  top: 70px;
  width: 95px;
}
.email {
  flex-shrink: 0;
  width: 360px;
  height: 68px;
  position: static;
}
.text-field2 {
  border-radius: 10px;
  border-style: solid;
  border-color: var(--black, #000000);
  border-width: 1px;
  width: 360px;
  height: 45px;
  position: absolute;
  left: 47px;
  top: 192px;
  font-size: 30px;
  padding-left: 10px;
}
.email2 {
  color: var(--light-grey, #979899);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: absolute;
  left: 53px;
  top: 169px;
}
.password {
  flex-shrink: 0;
  width: 360px;
  height: 67px;
  position: static;
}
.text-field3 {
  border-radius: 10px;
  border-style: solid;
  border-color: var(--black, #000000);
  border-width: 1px;
  width: 360px;
  height: 45px;
  position: absolute;
  left: 47px;
  top: 290px;
  font-size: 30px;
  padding-left: 10px;
}
.password2 {
  color: var(--light-grey, #979899);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: absolute;
  left: 53px;
  top: 268px;
}
.confirm-password {
  flex-shrink: 0;
  width: 360px;
  height: 67px;
  position: static;
}
.text-field4 {
  border-radius: 10px;
  border-style: solid;
  border-color: var(--black, #000000);
  border-width: 1px;
  width: 360px;
  height: 45px;
  position: absolute;
  left: 47px;
  top: 388px;
  font-size: 30px;
  padding-left: 10px;
}
.confirm-password2 {
  color: var(--light-grey, #979899);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: absolute;
  left: 53px;
  top: 366px;
  width: 186px;
}

.enter-accounttype {
  flex-shrink: 0;
  width: 360px;
  height: 67px;
  position: static;
}
.text-field5 {
  border-radius: 10px;
  border-style: solid;
  border-color: var(--black, #000000);
  border-width: 1px;
  width: 360px;
  height: 45px;
  position: absolute;
  left: 71px;
  top: 486px;
  font-size: 30px;
  padding-left: 10px;
}
.enter-accounttype2 {
  color: var(--light-grey, #979899);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: absolute;
  left: 77px;
  top: 464px;
  width: 250px;
}

.button-primary {
  background: var(--grey, #303030);
  border-radius: 10px;
  padding: 15px 157px 15px 157px;
  display: flex;
  flex-direction: row;
  gap: 10px;
  align-items: flex-start;
  justify-content: flex-end;
  flex-shrink: 0;
  position: relative;
  color: #ffffff;
  width: 370px;
  margin-top: 10px;
}
.signup-button {
  color: var(--white, #ffffff);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: relative;
}
.welcome {
  background: linear-gradient(to bottom, #ffffff, #f0f0f0);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-align: left;
  font-family: "Inter-Bold", sans-serif;
  font-size: 48px;
  font-weight: 700;
  position: absolute;
  left: 42%;
  top: 193px;
  width: 392px;
  height: 66px;
}
.purple-square {
  width: 150px; /* adjust as needed */
  height: 64px; /* adjust as needed */
  background-color: #da73eb;
  border-radius: 10px;
  position: absolute; /* changed from absolute to relative */
  right: 5%;
  top: 56px;
}

.home-page-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) scale(1); /* adjust the scale value as needed */
}
</style>
