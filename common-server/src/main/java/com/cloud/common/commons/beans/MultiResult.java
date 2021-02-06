/**	
 * <br>
 * @version
 * @created 2015年10月29日
 * @last Modified 
 * @history
 */

package com.cloud.common.commons.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* ClassName: MultiResult <br/>
* Function: 列表式数据封装类 <br/>
* date: 2016年5月31日 上午10:35:37 <br/>
* @author lcma
* @version @param <T>
* @since JDK 1.7
*/
@Data
public class MultiResult<T> extends BaseResult implements Serializable {

    private static final long serialVersionUID = 3045864717132369891L;

    public MultiResult(String desc) {
        super(desc);
    }

    public MultiResult(int code, String desc){
        super(code, desc);
    }

    public MultiResult(KeyValuePair keyValuePair) {
        super(keyValuePair);
    }

    /**
    * 列表数据封装
    */
   @ApiModelProperty(value = "列表数据")
   private List<T> rows ;

   /**
    * 结果集的数量
    */
   @ApiModelProperty(value = "总记录数")
   private long total ;

   /**
    * 特殊处理的
    */
   @ApiModelProperty(value = "特殊处理的数据")
   private Map<Object,Object> data;

    public MultiResult() {
        super();
    }

}




