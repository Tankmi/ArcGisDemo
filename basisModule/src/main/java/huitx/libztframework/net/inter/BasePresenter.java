package huitx.libztframework.net.inter;

/**
 * 业务逻辑基类
 *
 * @param <T>
 */
public interface BasePresenter<T> {

    int pageIndex = 1, pageRowSize = 8;

    void attachView(T view);

    void detachView();
}
