<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <permission-table
            v-if="permissions && permissions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:permissions="permissions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-permissions="getAllPermissions"
             >

            </permission-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PermissionTable from "@/components/PermissionTable";
import PermissionService from "../services/PermissionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PermissionTable,
  },
  data() {
    return {
      permissions: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllPermissions(sortBy='permissionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PermissionService.getAllPermissions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.permissions.length) {
					this.permissions = response.data.permissions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching permissions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching permission details:", error);
      }
    },
  },
  mounted() {
    this.getAllPermissions();
  },
  created() {
    this.$root.$on('searchQueryForPermissionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPermissions();
    })
  }
};
</script>
<style></style>
