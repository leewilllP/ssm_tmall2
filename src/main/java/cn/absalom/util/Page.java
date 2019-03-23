package cn.absalom.util;

/*
* 分页
* */
public class Page {
    private int start; //开始页数
    private int count; //每页显示个数
    private int total; //总个数
    private  String param;//参数

    private static final int defaultCount = 5;//每页五个

    public Page() {
        count = defaultCount;//默认五个
    }

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }
    //判断是否首页
    public boolean isHasPreviouse(){
        if (start==0)
            return false;
        return true;
    }
    //判断是否有下一页
    public boolean isHasNext(){
        if (start==getLast())
            return false;
        return true;
    }
    //返回下一页
    public int getLast(){
        int last;//最后一页开始那条
        //返回最后一页开始那条
        if (0 == total %count)
            last = total - count;
        else//如果有余数,那么条数+余数
            last = total - total%count;
        last = last<0?0:last; //last<0？ 小于则为0 不则返回
        return last;
    }
    public int getTotalPage(){
        int totalPage;
        //假设总数是50，能被5整除，那么有10页
        if(0 == total % count)
            totalPage =total/count;
        //不是整数多一页
        else
            totalPage =total/count+1;
        //至少显示一页
        if(0 == totalPage){
            totalPage = 1;
        }
        return totalPage;
    }


    //toString
    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
