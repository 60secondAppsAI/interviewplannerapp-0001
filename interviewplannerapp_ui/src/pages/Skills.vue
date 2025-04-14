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
            <skill-table
            v-if="skills && skills.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:skills="skills"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-skills="getAllSkills"
             >

            </skill-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import SkillTable from "@/components/SkillTable";
import SkillService from "../services/SkillService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    SkillTable,
  },
  data() {
    return {
      skills: [],
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
    async getAllSkills(sortBy='skillId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await SkillService.getAllSkills(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.skills.length) {
					this.skills = response.data.skills;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching skills:", error);
        }
        
      } catch (error) {
        console.error("Error fetching skill details:", error);
      }
    },
  },
  mounted() {
    this.getAllSkills();
  },
  created() {
    this.$root.$on('searchQueryForSkillsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllSkills();
    })
  }
};
</script>
<style></style>
