package hsj.stock.com.entry;

import com.kymjs.app.base_res.utils.base.entry.PageInfo;
import com.kymjs.app.base_res.utils.base.entry.stock.StockInfo;

import java.util.List;

/**
 * Created by 16486 on 2020/11/17.
 */

public class StockInfoList extends PageInfo {

    List<StockInfo> Result;

    public List<StockInfo> getResult() {
        return Result;
    }

    public void setResult(List<StockInfo> result) {
        Result = result;
    }
}
