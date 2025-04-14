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
            <department-table
            v-if="departments && departments.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:departments="departments"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-departments="getAllDepartments"
             >

            </department-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import DepartmentTable from "@/components/DepartmentTable";
import DepartmentService from "../services/DepartmentService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    DepartmentTable,
  },
  data() {
    return {
      departments: [],
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
    async getAllDepartments(sortBy='departmentId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await DepartmentService.getAllDepartments(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.departments.length) {
					this.departments = response.data.departments;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching departments:", error);
        }
        
      } catch (error) {
        console.error("Error fetching department details:", error);
      }
    },
  },
  mounted() {
    this.getAllDepartments();
  },
  created() {
    this.$root.$on('searchQueryForDepartmentsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllDepartments();
    })
  }
};
</script>
<style></style>
