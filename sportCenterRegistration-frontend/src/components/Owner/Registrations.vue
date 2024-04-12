<template>
  <div>
    <div>
      <el-input v-model="accountName" style="width:200px" placeholder="Input an account name"></el-input>
      <el-input v-model="sessionId" style="width:200px" placeholder="Input a session ID"></el-input>
      <el-button type="primary" round @click="searchRegistrations">Search</el-button>
    </div>
    <div style="margin:10px 0;">
      <el-button type="danger" plain round @click="deleteSelectedRegistrations">Mass Delete</el-button>
    </div>
    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50px" align="center"></el-table-column>
      <el-table-column prop="id" label="ID" align="center"></el-table-column>
      <el-table-column prop="date" label="Date" align="center"></el-table-column>
      <el-table-column prop="account.username" label="Account" align="center"></el-table-column>
      <el-table-column prop="session.id" label="Session ID" align="center"></el-table-column>
      <el-table-column label="Actions" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" round @click="deleteRegistration(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Create Registration" :visible.sync="createRegistrationDialogVisible" width="30%">
      <!-- Form to create a new registration -->
      <!-- Add input fields for date, account name, and session ID -->
      <!-- Add buttons to confirm or cancel -->
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      accountName: '',
      sessionId: '',
      tableData: [],
      selectedRegistrations: [],
      createRegistrationDialogVisible: false
    };
  },
  methods: {
    searchRegistrations() {
      // Fetch registrations based on accountName and sessionId
      axios.get(`/registration?accountName=${this.accountName}&sessionId=${this.sessionId}`)
        .then(response => {
          this.tableData = response.data;
        })
        .catch(error => {
          console.error('Error fetching registrations:', error);
        });
    },
    deleteRegistration(registrationId) {
      // Delete a registration by its ID
      axios.delete(`/registration?accountName=${this.accountName}&sessionId=${registrationId}`)
        .then(() => {
          // Remove the deleted registration from the table
          const index = this.tableData.findIndex(registration => registration.id === registrationId);
          if (index !== -1) {
            this.tableData.splice(index, 1);
          }
        })
        .catch(error => {
          console.error('Error deleting registration:', error);
        });
    },
    deleteSelectedRegistrations() {
      // Delete multiple registrations
      if (this.selectedRegistrations.length === 0) {
        return; // No registrations selected
      }

      // Create an array to store promises for each deletion
      const deletePromises = this.selectedRegistrations.map(registration => {
        return axios.delete(`/registration?accountName=${this.accountName}&sessionId=${registration.id}`);
      });

      // Execute all deletion promises
      Promise.all(deletePromises)
        .then(() => {
          // Clear selectedRegistrations array
          this.selectedRegistrations = [];
          // Refresh table data
          this.searchRegistrations();
        })
        .catch(error => {
          console.error('Error deleting registrations:', error);
        });
    },
    handleSelectionChange(selection) {
      this.selectedRegistrations = selection;
    }
  }
};
</script>