<template>
  <div class="container">
    <h1 class="title">项目介绍</h1>

    <section class="section">
      <h2 class="section-title">1. 项目背景</h2>
      <p class="section-content">
        随着社会分工细化，企业越来越注重内部协作和管理，对于团队协作管理平台的需求不断增加。
        相比较于一般的社交软件，TeamSphere更加侧重于用户组织间的交流协作，以沟通为基础，添加了许多专业的协作功能，比如文件共享，会议预定，日程管理等，可以帮助组织更加高效地完成各种工作。而和其他的文档协作平台相比，TeamSphere也可以更加轻松地实现成员间地沟通交流，为团队协作带来一站式的舒适体验。
      </p>
    </section>

    <section class="section">
      <h2 class="section-title">1.2 项目目的</h2>
      <p class="section-content">
        本项目旨在为中小型团体提供一个一站式内部协作管理的平台，使用户能够便捷安全地进行组织管理、会议通信、工作安排等操作；并提供后台管理功能，帮助管理员进行会议管理、数据统计以及安全性和权限控制。
        TeamSphere平台集成多种功能，同时注重用户体验和安全性，符合市场的广泛认可和需求；通过不断提供更新的功能、持续改进用户体验和提供高质量的服务，该平台可以与企业共同成长，并保持可持续发展。
      </p>
    </section>

    <section class="section">
      <h2 class="section-title">2. 整体描述</h2>
      <h3 class="subsection-title">2.1 子系统与主要功能</h3>
      <p class="section-content">
        TeamSphere的主要功能如下：
        <ul>
          <li>用户子系统：
            <ul>
              <li>用户注册和登录：用户可以注册新账号并使用已注册的账号登录系统。</li>
              <li>个人信息管理：用户可以编辑和更新个人信息，包括姓名、头像、联系方式等。</li>
              <li>个人日程管理：用户可以查看和管理个人的日程安排，包括任务截止日期和会议日程。</li>
              <li>个人项目管理：用户可以浏览并选择加入不同的项目，参与项目的任务分配和讨论，以及创建新的项目。</li>
            </ul>
          </li>
          <li>聊天子系统：
            <ul>
              <li>项目讨论组：用户可以加入项目的讨论组，与其他项目成员进行实时的聊天和讨论。</li>
              <li>创建讨论组：用户可以创建新的讨论组，并邀请其他项目成员加入。</li>
              <li>发送消息：用户可以在讨论组中发送消息，与团队成员交流和讨论项目相关事项。</li>
            </ul>
          </li>
          <li>项目管理子系统：
            <ul>
              <li>成员管理：管理员可以管理项目中的成员，包括邀请新成员与移除成员。</li>
              <li>权限管理：项目拥有者可以管理项目中成员的权限，包括任命管理员。</li>
              <li>会议预定：项目管理员可以预定会议，并设置会议的主题、日期、时间和参会人员。</li>
              <li>任务分配：项目管理员可以为项目成员分配任务，并设置任务的截止日期和优先级。</li>
              <li>提交任务：项目成员可以提交已完成的任务，上传证明材料，由管理员进行任务审核。</li>
              <li>文档管理：项目管理员可以管理项目的文件。</li>
              <li>上传文件：项目成员可以上传项目文件。</li>
            </ul>
          </li>
          <li>报表子系统：
            <ul>
              <li>查看项目数据：项目管理员可以查看项目的相关数据。</li>
              <li>导出报表：项目管理员可以导出项目数据报表。</li>
            </ul>
          </li>
        </ul>
      </p>
    </section>

    <section class="section">
      <h3 class="subsection-title">2.2 项目特点</h3>
      <p class="section-content">
        <ul>
          <li>多功能性：TeamSphere具有多个功能模块，包括项目管理、会议管理、聊天讨论和个人日程管理等。这种多功能性使得团队成员可以在一个集成的平台上进行协作和管理，提高了工作效率。</li>
          <li>实时协作：项目中的聊天讨论和讨论组功能提供了实时的沟通和协作环境。团队成员可以随时交流想法、讨论问题，并迅速做出决策和行动。</li>
          <li>任务驱动：TeamSphere具有完整的任务管理流程，通过任务的形式推动项目的发展，有利于项目的踏实进行。</li>
          <li>会议管理：会议管理子系统允许项目管理员预定会议并邀请参会人员。这样可以确保会议的顺利进行，并提高会议的效率。</li>
          <li>用户角色和权限管理：后台管理子系统允许管理员管理用户账号权限。通过设置不同的角色和权限，可以确保团队成员只能访问和操作他们需要的信息和功能，保护项目的安全性和保密性。</li>
        </ul>
      </p>
    </section>

    <section class="section">
      <h2 class="section-title">3. 系统与其他系统的接口</h2>
      <h3 class="subsection-title">3.1 图表接口</h3>
      <p class="section-content">
        TeamSphere可以与外部图表接口进行集成，以展示一些后台数据统计。
        <ul>
          <li>项目成员的考勤数据：可以使用外部图表接口来展示项目成员的考勤情况，例如每个成员的出勤率、迟到次数、早退次数等。这样可以帮助项目管理员和团队成员了解项目成员的工作情况。</li>
          <li>会议的出席数据：可以使用外部图表接口来展示会议的出席情况，例如每个参会人员的出席率、缺席率等。这样可以帮助项目管理员评估会议的有效性和参与度。</li>
          <li>项目的完成进度：可以使用外部图表接口来展示项目的完成进度，例如任务的完成情况、项目里程碑的达成情况等。这样可以帮助项目管理员和团队成员了解项目的整体进展，并及时调整计划和资源分配。</li>
        </ul>
        通过与外部图表接口的集成，TeamSphere可以提供直观、可视化的数据统计，帮助团队成员更好地了解和分析项目的情况。
      </p>
    </section>

    <section class="section">
      <h3 class="subsection-title">3.2 存储接口</h3>
      <p class="section-content">
        TeamSphere使用OSS对象存储服务存储项目中的文件，为项目管理提供文档管理。
      </p>
    </section>

    <section class="section">
      <h3 class="subsection-title">3.3 会议接口</h3>
      <p class="section-content">
        TeamSphere的会议功能可以通过调用第三方会议平台提供的API来实现，例如飞书提供的会议API。
        <ul>
          <li>会议预定：项目管理员可以在TeamSphere中预定会议，并使用飞书的API将会议信息发送到飞书平台。可以设置会议的主题、日期、时间和参会人员，并生成会议链接和凭证。</li>
          <li>会议状态和出席情况跟踪：通过调用飞书的API，TeamSphere可以获取会议的状态信息，包括会议开始时间、结束时间和参会人员的出席情况。可以实时更新会议状态，并提供给项目管理员和团队成员查看。</li>
        </ul>
        通过调用第三方会议平台（如Zoom）提供的API，TeamSphere可以与会议系统进行集成，实现全面的会议管理功能。这样可以使项目管理员和团队成员能够方便地预定、加入和参与项目会议，并获取会议的实时状态和记录。
      </p>
    </section>
  </div>
</template>

<script setup>
</script>

<style scoped>
.container {
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  animation: fadeIn 1s ease-in-out;
  height: 90vh;
  width: 85vw;
  overflow-y: auto;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
  }
}

.title {
  text-align: center;
  color: #333;
  font-size: 2.5em;
  margin-bottom: 20px;
  animation: slideIn 1s ease-in-out;
}

.section {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;
}

.section:hover {
  transform: translateY(-10px);
}

.section-title {
  color: #007bff;
  font-size: 1.8em;
  margin-bottom: 10px;
}

.subsection-title {
  color: #17a2b8;
  font-size: 1.5em;
  margin-top: 20px;
  margin-bottom: 10px;
}

.section-content {
  color: #555;
  font-size: 1.1em;
  line-height: 1.6;
}

ul {
  padding-left: 20px;
  list-style-type: disc;
}

li {
  margin-bottom: 10px;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}
</style>