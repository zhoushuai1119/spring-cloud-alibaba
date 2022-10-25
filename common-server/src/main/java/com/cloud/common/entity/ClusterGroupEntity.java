package com.cloud.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoushuai
 */
@Data
public class ClusterGroupEntity implements Serializable {

    private static final long serialVersionUID = 4548223899553986703L;

    /**
     * token server ip
     */
    private String ip;

    /**
     * token server port
     */
    private Integer port;

    /**
     * max allowed qps
     */
    private double maxAllowedQps;

}
