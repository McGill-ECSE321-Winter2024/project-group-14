<template>
    <div>
        <div class="enter-session-info-text">Enter session info</div>
        <div class="session-info-box">
            <form>
                <div class = "container">
                    <div class = "instructor-name">
                        <label class = "instructor-name-label" for="instructor-name">Enter instructor's name</label>
                        <input v-model="instructorName" class = "instructor-name-textfield" type="text" name="instructor-name" required></input>
                    </div>
                    <div class = "sportclass-name">
                        <label class = "sport-class-label" for="sport-class-name">Enter sport class name</label>
                        <input v-model="sportClassName" class = "sport-class-textfield" type="text" name="sport-class-name" required></input>
                    </div>
                    <div class = "start-time">
                        <label class = "start-time-label" for="start-time">Enter session start time</label>
                        <input v-model="startTime" class = "start-time-textfield" type="text" name="start-time" required></input>
                    </div>
                    <div class = "end-time">
                        <label class = "end-time-label" for="end-time">Enter session end time</label>
                        <input v-model="endTime" id = "endTimeInput" class = "end-time-textfield" type="text" name="end-time" required></input>
                    </div>
                    <div class = "location">
                        <label class = "location-label" for="locationInp">Enter location</label>
                        <input v-model="location" id = "locationInput" class = "location-textfield" type="text" name="locationInp" required></input>
                    </div>
                    <div class = 'date-picker'>
                        <input v-model="date" class = 'picker' type="date"/>
                    </div>
                </div>
            </form>
        </div>
        <div class="cancel-button"><a href="/#/InstructorSpc" class="button-text-cancel">Cancel</a></div>
        <div class="confirm-button" @click="realCreateSession()"><a class="button-text-confirm">Confirm</a></div>
    </div>
</template>
  
<script src="./js/CreateSession.js">
  
</script>
  
<style>
    .enter-session-info-text {
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
        left: calc(30%);
        top: 70px;
        width: 359px;
        height: 73px;
    }

    .session-info-box {
        background: var(--white, #ffffff);
        border-radius: 12px;
        padding: 45px 40px 70px 40px;
        display: flex;
        flex-direction: column;
        gap: 31px;
        align-items: center;
        justify-content: flex-start;
        width: 502px;
        height: 500px;
        position: absolute;
        left: 500px;
        top: 170px;
    }

    .instructor-name {
        flex-shrink: 0;
        width: 360px;
        height: 68px;
        position: static;
    }

    .instructor-name-label {
        color: var(--light-grey, #979899);
        text-align: left;
        font-family: "Inter-Medium", sans-serif;
        font-size: 16px;
        font-weight: 500;
        position: absolute;
        left: 77px;
        top: 25px;
        width: 354px;
    }

    .instructor-name-textfield {
        border-radius: 10px;
        border-style: solid;
        border-color: var(--black, #000000);
        border-width: 1px;
        width: 360px;
        height: 45px;
        position: absolute;
        left: 71px;
        top: 48px;
    }

    .sportclass-name {
        flex-shrink: 0;
        width: 360px;
        height: 85px;
        position: static;
    }

    .sport-class-label {
        color: var(--light-grey, #979899);
        text-align: left;
        font-family: "Inter-Medium", sans-serif;
        font-size: 16px;
        font-weight: 500;
        position: absolute;
        left: 77px;
        top: 90px;
        width: 354px;
    }

    .sport-class-textfield {
        border-radius: 10px;
        border-style: solid;
        border-color: var(--black, #000000);
        border-width: 1px;
        width: 360px;
        height: 45px;
        position: absolute;
        left: 71px;
        top: 113px;
    }

    .start-time-label {
        color: var(--light-grey, #979899);
        text-align: left;
        font-family: "Inter-Medium", sans-serif;
        font-size: 16px;
        font-weight: 500;
        position: absolute;
        left: 77px;
        top: 155px;
        width: 354px;
    }

    .start-time-textfield {
        border-radius: 10px;
        border-style: solid;
        border-color: var(--black, #000000);
        border-width: 1px;
        width: 360px;
        height: 45px;
        position: absolute;
        left: 71px;
        top: 178px;
    }

    .end-time {
        flex-shrink: 0;
        width: 360px;
        height: 98px;
        position: static;
    }

    .end-time-label {
        color: var(--light-grey, #979899);
        text-align: left;
        font-family: "Inter-Medium", sans-serif;
        font-size: 16px;
        font-weight: 500;
        position: absolute;
        left: 77px;
        top: 220px;
        width: 354px;
    }

    .end-time-textfield {
        border-radius: 10px;
        border-style: solid;
        border-color: var(--black, #000000);
        border-width: 1px;
        width: 360px;
        height: 45px;
        position: absolute;
        left: 71px;
        top: 243px;
    }

    .location {
        flex-shrink: 0;
        width: 360px;
        height: 98px;
        position: static;
    }

    .location-label {
        color: var(--light-grey, #979899);
        text-align: left;
        font-family: "Inter-Medium", sans-serif;
        font-size: 16px;
        font-weight: 500;
        position: absolute;
        left: 77px;
        top: 285px;
        width: 354px;
    }

    .location-textfield {
        border-radius: 10px;
        border-style: solid;
        border-color: var(--black, #000000);
        border-width: 1px;
        width: 360px;
        height: 45px;
        position: absolute;
        left: 71px;
        top: 308px;
    }

    .confirm-button {
        background: var(--grey, #303030);
        border-radius: 10px;
        padding: 15px 157px 15px 157px;
        display: flex;
        flex-direction: row;
        gap: 10px;
        align-items: flex-start;
        justify-content: flex-end;
        flex-shrink: 0;
        position: absolute;
        left: 65px;
        top: 620px;
    }

    .cancel-button {
        background: var(--grey, #303030);
        border-radius: 10px;
        padding: 15px 157px 15px 157px;
        display: flex;
        flex-direction: row;
        gap: 10px;
        align-items: flex-start;
        justify-content: flex-end;
        flex-shrink: 0;
        position: absolute;
        left: 65px;
        top: 510px;
    }

    .date-picker {
        flex-shrink: 0;
        width: 100px;
        height: 50px;
        position: absolute;
        top: 340;
    }

    .button-text-confirm{
        color: var(--white, #ffffff);
        text-align: left;
        font-family: "Inter-Medium", sans-serif;
        font-size: 16px;
        font-weight: 500;
        position: relative;
    }

    .button-text-cancel{
        color: var(--white, #ffffff);
        text-align: left;
        font-family: "Inter-Medium", sans-serif;
        font-size: 16px;
        font-weight: 500;
        position: relative;
    }
</style>
