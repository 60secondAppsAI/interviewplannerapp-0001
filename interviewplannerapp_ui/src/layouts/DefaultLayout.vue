<template>
  <a-layout style="min-height: 100vh">
    <!-- Header -->
    <a-layout-header style="background: #fff; padding: 0">
      <app-header />
    </a-layout-header>

    <!-- Main layout with sider and content -->
    <a-layout>
      <!-- Collapsible Sider with Controlled Open Keys for Nested Menu -->
      <a-layout-sider width="200px" collapsible style="background: #fff">
        <a-menu
          mode="inline"
          :openKeys="openKeys"
          @openChange="onOpenChange"
          style="height: 100%; border-right: 0"
        >
<!--          <a-sub-menu key="sub1" title="Menu 1">
            <a-menu-item key="1">Option 1</a-menu-item>
            <a-menu-item key="2">Option 2</a-menu-item>
            <a-sub-menu key="sub1-1" title="Nested Menu">
              <a-menu-item key="3">Option 3</a-menu-item>
              <a-menu-item key="4">Option 4</a-menu-item>
            </a-sub-menu>
          </a-sub-menu>
          <a-sub-menu key="sub2" title="Menu 2">
            <a-menu-item key="5">Option 5</a-menu-item>
            <a-menu-item key="6">Option 6</a-menu-item>
          </a-sub-menu>
-->>

	 
			
				<a-menu-item>
					<router-link to="/roles">
						<span class="label">Roles</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/educations">
						<span class="label">Educations</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/experiences">
						<span class="label">Experiences</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/accounts">
						<span class="label">Accounts</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/applicationForms">
						<span class="label">Application Forms</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/skills">
						<span class="label">Skills</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/documentations">
						<span class="label">Documentations</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/permissions">
						<span class="label">Permissions</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/meetingNotes">
						<span class="label">Meeting Notes</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/questions">
						<span class="label">Questions</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/interviews">
						<span class="label">Interviews</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/interviewers">
						<span class="label">Interviewers</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/interviewSlots">
						<span class="label">Interview Slots</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/feedbacks">
						<span class="label">Feedbacks</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/candidates">
						<span class="label">Candidates</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/jobDescriptions">
						<span class="label">Job Descriptions</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/departments">
						<span class="label">Departments</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/focusAreas">
						<span class="label">Focus Areas</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/resumes">
						<span class="label">Resumes</span>
					</router-link>
				</a-menu-item>
		
	
        </a-menu>
      </a-layout-sider>
      
      <!-- Content area -->
      <a-layout-content style="padding: 16px">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import { ref } from "vue";
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "DefaultLayout",
  components: {
    AppHeader,
  },
  setup() {
    const openKeys = ref(["sub1"]); // default open submenu
    const onOpenChange = (keys) => {
      // Open only the last clicked submenu, for single open behavior
      openKeys.value = keys.length ? [keys[keys.length - 1]] : [];
    };

    return {
      openKeys,
      onOpenChange,
    };
  },
};
</script>
<style>
.ant-layout {
    margin-top: 20px;
}
</style>

