<template>
    <div>
        <div class="customerMessage">
            Add classes
        </div>

        <div class="table">
            <table>
                <thead>
                    <tr>
                        <th></th>
                        <th>Class Name</th>
                        <th>Instructor</th>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Location</th>
                        <th>ID</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(placeholderSession, index) in sessions" :key="placeholderSession.id">
                        <td>{{ index + 1 }}</td>
                        <td> {{ placeholderSession.sportClassName }} </td>
                        <td> {{ placeholderSession.instructorName }} </td>
                        <td> {{ placeholderSession.date }} </td>
                        <td> {{ placeholderSession.startTime }} </td>
                        <td> {{ placeholderSession.endTime }} </td>
                        <td> {{ placeholderSession.location }} </td>
                        <td> {{ placeholderSession.id }} </td>
                    </tr>
                </tbody>
            </table>
        </div>
  </div>

</template>

<script src="./js/CustomerAddClasses">

</script>

<style>
    .customerMessage {
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
    }
</style>
