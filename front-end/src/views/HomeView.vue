<template>
  <div class="platform-overview">
    <section class="introduction">
      <h1>软件测试平台介绍</h1>
      <p>欢迎来到软件测试平台！我们的平台旨在为开发者和测试人员提供一个直观、全面的测试工具，通过可视化界面让测试变得简单高效。平台分为两大主要部分：<strong>练习</strong>和<strong>项目</strong>，以满足不同的需求和使用场景。</p>
    </section>

    <section class="platform-structure">
      <h2>平台结构</h2>

      <div class="module">
        <h3>1. 练习模块</h3>
        <p><strong>练习模块</strong>主要面向场景简单的测试练习项目，通过一系列模拟和实操来提升测试技能。此模块包括：</p>
        <ul>
          <li><strong>项目设置</strong>：了解和配置项目的基本测试环境，掌握项目启动的第一步。</li>
          <li><strong>测试</strong>：进行基础的功能测试和性能测试，确保每一个功能模块的正常运行。</li>
        </ul>
        <p><strong>主要功能：</strong></p>
        <ul>
          <li><strong>自动化测试工具</strong>：提供丰富的自动化测试工具，减少重复性工作，提高测试效率。</li>
          <li><strong>测试报告生成</strong>：自动生成测试报告，详细记录测试过程和结果，方便后续分析和改进。</li>
          <li><strong>可视化图表</strong>：给出某次测试的的图表分析结果，帮助用户直观了解测试情况。</li>
        </ul>
      </div>

      <div class="module">
        <h3>2. 项目模块</h3>
        <p><strong>项目模块</strong>则主要针对实际项目的全面测试，帮助测试完整项目。此模块包括以下几个部分：</p>
        <ul>
          <li><strong>项目介绍</strong>：概览项目的背景、目标和测试需求，为后续测试提供依据。</li>
          <li><strong>单元测试</strong>：对项目的基本单元进行测试，确保每个单元模块的独立性和正确性。</li>
          <li><strong>集成测试</strong>：对各个模块进行集成测试，确保各模块在一起工作时能够协调运行。</li>
          <li><strong>系统测试</strong>：在完整系统环境中进行全面测试，发现系统级别的问题。</li>
          <li><strong>性能测试</strong>：通过高强度的性能测试，确保系统在高负载情况下依然稳定运行。</li>
        </ul>
        <p><strong>主要功能：</strong></p>
        <ul>
          <li><strong>全面测试覆盖</strong>：从单元测试到系统测试，覆盖项目开发的每一个阶段，确保质量。</li>
          <li><strong>性能监控</strong>：实时监控系统性能，提供详细的性能报告，帮助优化系统性能。</li>
          <li><strong>问题追踪</strong>：记录和追踪发现的问题，提供解决建议，帮助快速修复。</li>
        </ul>
      </div>
    </section>

    <section class="platform-advantages">
      <h2>平台优势</h2>
      <ul>
        <li><strong>可视化界面</strong>：简洁直观的用户界面，降低学习曲线，提高使用效率。</li>
        <li><strong>自动化流程</strong>：通过自动化工具和脚本，减少手工操作，提高测试准确性。</li>
        <li><strong>实时反馈</strong>：测试过程中的实时反馈，帮助用户及时发现和修复问题。</li>
        <li><strong>报告分析</strong>：详细的测试报告和分析工具，帮助用户全面了解测试情况，做出优化决策。</li>
      </ul>
    </section>

    <section class="conclusion">
      <h2>结语</h2>
      <p>软件测试平台致力于为广大开发者和测试人员提供一个专业、高效的测试环境。无论是初学者还是专业测试人员，都能在这里找到适合自己的测试工具和方法。通过我们的平台，您的测试工作将变得更加简单、快捷和高效。</p>
      <p>开始使用软件测试平台，提升您的测试效率和质量，为您的项目保驾护航！</p>
    </section>
  </div>
</template>

<script setup>
// No JavaScript logic needed for static content
</script>

<style scoped>
.platform-overview {
  position: relative;
  font-family: Arial, sans-serif;
  color: #333;
  margin: 0;
  height: 100vh;
  width: 100%;
  overflow-y: auto;
}

.platform-overview::before {
  content: "";
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: url('@/assets/imgs/HomeBg.jpg');
  background-size: cover;
  background-position: center;
  opacity: 0.5;
  z-index: -1;
}

.introduction,
.platform-structure,
.platform-advantages,
.conclusion {
  margin: 10px;
  background: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 10px;
}

h1, h2, h3 {
  color: #0056b3;
}

h1 {
  font-size: 2.5em;
  margin-bottom: 10px;
}

h2 {
  font-size: 2em;
  margin-bottom: 10px;
}

h3 {
  font-size: 1.5em;
  margin-bottom: 10px;
}

p {
  line-height: 1.6;
}

ul {
  list-style-type: disc;
  padding-left: 20px;
}

ul li {
  margin-bottom: 10px;
}

.module {
  margin-bottom: 20px;
}

strong {
  color: #0056b3;
}
</style>
