<template>
  <div id="ApproveNewClass">
    <header-component></header-component>
    
    <div class="main-content">
      <h1>Approve for new class type</h1>
      <p>Select New class type to make the change</p>

      <table-component :class-types="classTypes"></table-component>

      <div class="action-buttons">
        <button-component text="Add"></button-component>
        <button-component text="Update"></button-component>
      </div>
    </div>
  </div>
</template>

<script>
</script>

<style>
  .main-content {
    padding: 20px;
  }

  .action-buttons {
    display: flex;
    justify-content: flex-end;
    padding: 20px;
  }
</style>