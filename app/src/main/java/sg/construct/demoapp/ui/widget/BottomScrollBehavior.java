package sg.construct.demoapp.ui.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
public class BottomScrollBehavior extends CoordinatorLayout.Behavior {

    public BottomScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, final View child, View dependency) {
        if (dependency instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) dependency;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int itemPosition = manager.findLastVisibleItemPosition();
                    if (itemPosition == recyclerView.getAdapter().getItemCount()-1) {
                        View view = manager.findViewByPosition(manager.findLastVisibleItemPosition());
                        int extraBot = view.getBottom() - recyclerView.getHeight();
//                        Log.v("@nmh", String.format("child %s height %s bottom %s", child.getHeight(), view.getBottom(), extraBot));
                        child.setTranslationY(child.getHeight() + extraBot);
                    }
                }
            });
            return true;
        }

        return false;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }


}
