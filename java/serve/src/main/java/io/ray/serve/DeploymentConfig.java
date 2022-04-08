package io.ray.serve;

import com.google.common.base.Preconditions;
import com.google.protobuf.ByteString;
import io.ray.runtime.serializer.MessagePackSerializer;
import io.ray.serve.generated.DeploymentLanguage;
import java.io.Serializable;

public class DeploymentConfig implements Serializable {

  private static final long serialVersionUID = 4037621960087621036L;

  private int numReplicas = 1;

  private int maxConcurrentQueries = 100;

  private Object userConfig;

  private double gracefulShutdownWaitLoopS = 2;

  private double gracefulShutdownTimeoutS = 20;

  private double healthCheckPeriodS = Constants.DEFAULT_HEALTH_CHECK_PERIOD_S;

  private double healthCheckTimeoutS = Constants.DEFAULT_HEALTH_CHECK_TIMEOUT_S;

  private AutoscalingConfig autoscalingConfig;

  private boolean isCrossLanguage;

  private int deploymentLanguage = 1;

  public int getNumReplicas() {
    return numReplicas;
  }

  public DeploymentConfig setNumReplicas(int numReplicas) {
    this.numReplicas = numReplicas;
    return this;
  }

  public int getMaxConcurrentQueries() {
    return maxConcurrentQueries;
  }

  public DeploymentConfig setMaxConcurrentQueries(int maxConcurrentQueries) {
    Preconditions.checkArgument(maxConcurrentQueries >= 0, "max_concurrent_queries must be >= 0");
    this.maxConcurrentQueries = maxConcurrentQueries;
    return this;
  }

  public Object getUserConfig() {
    return userConfig;
  }

  public DeploymentConfig setUserConfig(Object userConfig) {
    this.userConfig = userConfig;
    return this;
  }

  public double getGracefulShutdownWaitLoopS() {
    return gracefulShutdownWaitLoopS;
  }

  public DeploymentConfig setGracefulShutdownWaitLoopS(double gracefulShutdownWaitLoopS) {
    this.gracefulShutdownWaitLoopS = gracefulShutdownWaitLoopS;
    return this;
  }

  public double getGracefulShutdownTimeoutS() {
    return gracefulShutdownTimeoutS;
  }

  public DeploymentConfig setGracefulShutdownTimeoutS(double gracefulShutdownTimeoutS) {
    this.gracefulShutdownTimeoutS = gracefulShutdownTimeoutS;
    return this;
  }

  public double getHealthCheckPeriodS() {
    return healthCheckPeriodS;
  }

  public void setHealthCheckPeriodS(double healthCheckPeriodS) {
    this.healthCheckPeriodS = healthCheckPeriodS;
  }

  public double getHealthCheckTimeoutS() {
    return healthCheckTimeoutS;
  }

  public void setHealthCheckTimeoutS(double healthCheckTimeoutS) {
    this.healthCheckTimeoutS = healthCheckTimeoutS;
  }

  public AutoscalingConfig getAutoscalingConfig() {
    return autoscalingConfig;
  }

  public DeploymentConfig setAutoscalingConfig(AutoscalingConfig autoscalingConfig) {
    this.autoscalingConfig = autoscalingConfig;
    return this;
  }

  public boolean isCrossLanguage() {
    return isCrossLanguage;
  }

  public DeploymentConfig setCrossLanguage(boolean isCrossLanguage) {
    this.isCrossLanguage = isCrossLanguage;
    return this;
  }

  public int getDeploymentLanguage() {
    return deploymentLanguage;
  }

  public DeploymentConfig setDeploymentLanguage(int deploymentLanguage) {
    this.deploymentLanguage = deploymentLanguage;
    return this;
  }

  public byte[] toProtoBytes() {
    return io.ray.serve.generated.DeploymentConfig.newBuilder()
        .setNumReplicas(numReplicas)
        .setMaxConcurrentQueries(maxConcurrentQueries)
        .setGracefulShutdownWaitLoopS(gracefulShutdownWaitLoopS)
        .setGracefulShutdownTimeoutS(gracefulShutdownTimeoutS)
        .setIsCrossLanguage(true)
        .setDeploymentLanguage(DeploymentLanguage.JAVA)
        .setUserConfig(
            ByteString.copyFrom(
                MessagePackSerializer.encode(userConfig)
                    .getKey())) // TODO controller distinguish java and deserialize it.
        .build()
        .toByteArray();
  }
}
