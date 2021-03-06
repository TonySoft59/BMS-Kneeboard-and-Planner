package com.oldgoat5.bmstacticalreference.navigation.balkans;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oldgoat5.bmstacticalreference.R;
import com.oldgoat5.bmstacticalreference.tools.views.ZoomImageView;

/********************************************************************
 * Copyright © Michael Evans - All Rights Reserved.
 *
 * @author Michael Evans
 * @since 6/7/2015
 ********************************************************************/
public class MontenegroBalkansChartFragment extends Fragment
{
    Context CONTEXT;

    private Dialog dialog;
    private ImageView imageView;
    private View view;
    private ZoomImageView zoomImageView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.montenegro_balkans_chart_fragment_layout, container, false);

        if (this.isAdded())
        {
            CONTEXT = getActivity();
        }

        imageView = (ImageView) view.findViewById(R.id.montenegro_balkans_image_view);

        dialog = new Dialog(CONTEXT);
        zoomImageView = new ZoomImageView(CONTEXT);

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                zoomImageView.setImageResource(R.drawable.montenegro_airbases);
                dialog.setContentView(zoomImageView);
                dialog.setTitle("Montenegro Airbases");
                dialog.show();
            }
        });

        return view;
    }
}
