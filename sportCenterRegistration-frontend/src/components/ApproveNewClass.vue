<template>
  <div id = "ApproveClass">
    <h2>Approve a New Class</h2>
    <p>Select new Class Type to Approve a Change </p>
    <table>
      <thead>
        <tr>
          <th>Sport Class Id</th>
          <th>Sport Class Name</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="sportClass in sportClasses" :key="sportClass.id">
          <td>{{ sportClass.id }}</td>
          <td>{{ sportClass.name }}</td>
            <td><button @click="performAction">Approve</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

</script>

<style>
  body {
    background: linear-gradient(to bottom, #61EDED, #999999);
    height: 100vh; 

  }

  
#ApproveClass{
    margin-top: 50px;
    margin-left: 50px;
    margin-right: 10px;
    padding-bottom: 0px;
    background : white;
    border: 1px solid #ccc;
    border-radius: 10px;

    
}
table {
  width: 100%;
  border-collapse: collapse;
}

table, th, td {
  border: 1px solid black;
}
th, td {
  padding: 8px;
  text-align: left;
}

h2 {
  text-align: center;
}
</style>
