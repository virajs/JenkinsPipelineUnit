package com.lesfurets.jenkins

import org.junit.Before
import org.junit.Test

import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS

class TestParallelJobCPS extends BasePipelineTestCPS {

    @Override
    @Before
    void setUp() throws Exception {
        scriptRoots += 'src/test/jenkins'
        super.setUp()
        def scmBranch = "feature_test"
        binding.setVariable('scm', [
                        $class                           : 'GitSCM',
                        branches                         : [[name: scmBranch]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions                       : [],
                        submoduleCfg                     : [],
                        userRemoteConfigs                : [[
                                                                            credentialsId: 'gitlab_git_ssh',
                                                                            url          : 'github.com/lesfurets/JenkinsPipelineUnit.git'
                                                            ]]
        ])
    }

    @Test
    void should_execute_parallel_with_errors() throws Exception {
        def script = runScript("job/parallelJob.jenkins")
        try{
            script.execute()
        } finally {
            printCallStack()
        }
        assertJobStatusFailure()
    }
}
