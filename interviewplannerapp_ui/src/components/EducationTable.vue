
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3 justify-content-end">
      <div class="col-8"></div>
       <div class="col-auto">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by BusinessUnit#, Location#, Operator#, City, State, FirstName, LastName…"
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Educations</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalEducations = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalEducations">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add Education</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="EducationId" type="text" placeholder="Enter EducationId" v-model="educationToAdd.educationId"></base-input>
  <base-input label="Institution" type="text" placeholder="Enter Institution" v-model="educationToAdd.institution"></base-input>
  <base-input label="Degree" type="text" placeholder="Enter Degree" v-model="educationToAdd.degree"></base-input>
  <base-input label="YearOfGraduation" type="text" placeholder="Enter YearOfGraduation" v-model="educationToAdd.yearOfGraduation"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="educations" :row-key="record => record.EducationId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <EducationPictureView :educations="educations" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import EducationService from "../services/EducationService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import EducationPictureView from './EducationPictureView.vue';


const educationsColumns = [
  "educationId",
  "year",
  "date",
  "competitionId",
  "educationId"
]

export default {
  props: {
    educations: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    EducationPictureView
  },

  data() {
    return {
      modalEducations: false,
        isTableView: true,

      columns: [
        {
          title: 'Education Id',
		dataIndex: 'educationId',
          visible: true,
          scopedSlots: { customRender: 'educationId' },
          sorter: true
          //sorter: (a, b) => a.educationId - b.educationId,
          //sorter: (a, b) => a.educationId.localeCompare(b.educationId),
        },
        {
          title: 'Institution',
		dataIndex: 'institution',
          visible: true,
          scopedSlots: { customRender: 'institution' },
          sorter: true
          //sorter: (a, b) => a.institution - b.institution,
          //sorter: (a, b) => a.institution.localeCompare(b.institution),
        },
        {
          title: 'Degree',
		dataIndex: 'degree',
          visible: true,
          scopedSlots: { customRender: 'degree' },
          sorter: true
          //sorter: (a, b) => a.degree - b.degree,
          //sorter: (a, b) => a.degree.localeCompare(b.degree),
        },
        {
          title: 'Year Of Graduation',
		dataIndex: 'yearOfGraduation',
          visible: true,
          scopedSlots: { customRender: 'yearOfGraduation' },
          sorter: true
          //sorter: (a, b) => a.yearOfGraduation - b.yearOfGraduation,
          //sorter: (a, b) => a.yearOfGraduation.localeCompare(b.yearOfGraduation),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} educations`,
      },

      educations: [],
      educationToAdd: {},

      educationsTable: {
        columns: [...educationsColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'educationId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderEducationsTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let educationsTableData = [];
      for (let i = 0; i < this.educations.length; i++) {
        educationsTableData.push({
          id: i,
          educationId: this.educations[i].educationId,
          year: this.educations[i].year,
          date: this.educations[i].date,
          competitionId: this.educations[i].competitionId,
          educationId: this.educations[i].educationId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-educations',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToResumeDetail(id) {
      this.$router.push({ name: 'ResumeDetail', params: { resumeId: id.toString() }})
    },
    routingToJobDescriptionDetail(id) {
      this.$router.push({ name: 'JobDescriptionDetail', params: { jobDescriptionId: id.toString() }})
    },
    routingToFocusAreaDetail(id) {
      this.$router.push({ name: 'FocusAreaDetail', params: { focusAreaId: id.toString() }})
    },
    routingToQuestionDetail(id) {
      this.$router.push({ name: 'QuestionDetail', params: { questionId: id.toString() }})
    },
    routingToInterviewDetail(id) {
      this.$router.push({ name: 'InterviewDetail', params: { interviewId: id.toString() }})
    },
    routingToInterviewerDetail(id) {
      this.$router.push({ name: 'InterviewerDetail', params: { interviewerId: id.toString() }})
    },
    routingToInterviewSlotDetail(id) {
      this.$router.push({ name: 'InterviewSlotDetail', params: { interviewSlotId: id.toString() }})
    },
    routingToFeedbackDetail(id) {
      this.$router.push({ name: 'FeedbackDetail', params: { feedbackId: id.toString() }})
    },
    routingToCandidateDetail(id) {
      this.$router.push({ name: 'CandidateDetail', params: { candidateId: id.toString() }})
    },
    routingToDepartmentDetail(id) {
      this.$router.push({ name: 'DepartmentDetail', params: { departmentId: id.toString() }})
    },
    routingToRoleDetail(id) {
      this.$router.push({ name: 'RoleDetail', params: { roleId: id.toString() }})
    },
    routingToEducationDetail(id) {
      this.$router.push({ name: 'EducationDetail', params: { educationId: id.toString() }})
    },
    routingToExperienceDetail(id) {
      this.$router.push({ name: 'ExperienceDetail', params: { experienceId: id.toString() }})
    },
    routingToAccountDetail(id) {
      this.$router.push({ name: 'AccountDetail', params: { accountId: id.toString() }})
    },
    routingToApplicationFormDetail(id) {
      this.$router.push({ name: 'ApplicationFormDetail', params: { applicationFormId: id.toString() }})
    },
    routingToSkillDetail(id) {
      this.$router.push({ name: 'SkillDetail', params: { skillId: id.toString() }})
    },
    routingToDocumentationDetail(id) {
      this.$router.push({ name: 'DocumentationDetail', params: { documentationId: id.toString() }})
    },
    routingToPermissionDetail(id) {
      this.$router.push({ name: 'PermissionDetail', params: { permissionId: id.toString() }})
    },
    routingToMeetingNoteDetail(id) {
      this.$router.push({ name: 'MeetingNoteDetail', params: { meetingNoteId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForEducationsChanged', this.searchQuery);
		//this.renderEducationsTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalEducations = false;

      const currentDate = new Date().getTime();
      this.educationToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.educationToAdd);
      console.log(jsonData);
      
      const res = await EducationService.addEducation(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderEducationsTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}
.header-search {
  width: 300px !important;
  margin-left: auto !important;
}
</style>
