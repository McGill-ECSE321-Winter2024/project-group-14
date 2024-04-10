<template>
  <div >
    <div class="login-page">
      <div class="login">
            <!-- Enter you email here  -->
            <div class="email">
            <input type="text" class="text-field" v-model="email" placeholder=""/>
            <div class="email2">Email</div>
            </div>
            <!-- Enter you password here  -->
            <div class="password">
            <input type="password" class="text-field2" v-model="password" placeholder="" />
            <div class="password2">Password</div>
            </div>
            <!-- Error login -->
            <div class="error-login">
            <h5 v-if="errorLogin" style="color: red; padding-top: 20px"
            >Error: {{ errorLogin }}</h5>
            </div>
            <!-- Make "Log in" button  -->
            <div class="button-primary">
            <button type="button" class="login-button"
            v-bind:disabled="!email || !password"
            @click="login(email, password)" >Log in</button>
            </div>
            <!-- Make "Sign Up" button  -->
            <div class="button-primary">
              <a href="/#/SignUp" class="signup-button" id="signup">Sign Up</a>
            </div>
            </div>
            <!-- Welcome back message  -->
            <div class="welcome-back">Welcome back</div>

            <!-- Make "HomePage" button  -->
            <div class="purple-square">
            <div class="HomePage-buttons">
            <a href="/#/Home" class="home-page-button">
            <img class="home-page" :src="require('@/assets/home-page0.png')" alt="Home Page Icon"  />
            </a>
            </div>
            </div>
    </div>
  </div>
</template>

<script src="./js/LoginPage.js"></script>

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

.login-page * {
box-sizing: border-box;
}

.login-page {
background: linear-gradient(
    180deg,
    rgba(97, 237, 237, 1) 0%,
    rgba(153, 153, 153, 1) 100%
  );
  height: 1024px;
  position: relative;
  overflow: hidden;
}
.login {
  background: var(--white, #ffffff);
  border-radius: 12px;
  padding: 70px 40px 70px 40px;
  display: flex;
  flex-direction: column;
  gap: 31px;
  align-items: center;
  justify-content: flex-start;
  position: absolute;
  left: 493px;
  top: 292px;
}
.email {
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
.email2 {
  color: var(--light-grey, #979899);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: absolute;
  left: 53px;
  top: 70px;
}
.password {
  flex-shrink: 0;
  width: 360px;
  height: 67px;
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
  top: 191px;
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
  top: 169px;
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
  z-index: 1;
}
.login-button{
  color: var(--white, #ffffff);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: relative;
  z-index: 2;
}
.signup-button {
  color: var(--white, #ffffff);
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  font-weight: 500;
  position: relative;
  z-index: 2;
}
.welcome-back {
  background: linear-gradient(
    108.92deg,
    rgba(223, 112, 132, 1) 10.000000149011612%,
    rgba(255, 0, 153, 1) 100%
  );
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-align: left;
  font-family: "Inter-Bold", sans-serif;
  font-size: 48px;
  font-weight: 700;
  position: absolute;
  left: calc(50% - 196px);
  top: 193px;
  width: 392px;
  height: 66px;
}
.purple-square {
  width: 150px; /* adjust as needed */
  height: 64px; /* adjust as needed */
  background-color: #da73eb;
  border-radius: 10px;
  position: relative; /* changed from absolute to relative */
  left: 1210px;
  top: 56px;
}

.home-page-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) scale(1); /* adjust the scale value as needed */
}
</style>
        
