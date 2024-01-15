/*
  GRANITE DATA SERVICES
  Copyright (C) 2007-2008 ADEQUATE SYSTEMS SARL

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
  for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.
*/

import mx.controls.Image;
import mx.core.BitmapAsset;
import mx.containers.Canvas;
import mx.core.UIComponent;
import mx.events.ItemClickEvent;

public static const THUMB_WIDTH:Number = 170;

private var _page:Number = 0;

public var blurring:Array = new Array();

public function updateThumb( page:Canvas ):void
{
    var scale:Number = THUMB_WIDTH / page.width;
    var matrix:Matrix = new Matrix();
    var bmpd:BitmapData = new BitmapData( THUMB_WIDTH, page.height * scale );
    var thumb:Thumbnail = Thumbnail( boxThumbs.getChildAt( boxForms.getChildIndex( page ) ) );

    matrix.scale( scale, scale );
    bmpd.draw( page, matrix );

    thumb.source = new BitmapAsset( bmpd, PixelSnapping.AUTO, true );
}

public function doCreationComplete( event:Event ):void
{
    var thumb:Thumbnail = Thumbnail( boxThumbs.getChildAt( 0 ) );

    thumb.selected = true;
    blurring.push( new BlurFilter( 64, 2 ) );
}

public function doEndMove( event:Event ):void
{
    cvsForms.filters = null;
}

public function doEndResize( event:Event ):void
{
    if( cvsHighlight.width == 0 )
    {
        cvsHighlight.visible = false;
    }
}





public function doMenu( event:ItemClickEvent ):void
{
    page = event.index;
}

public function doNext( event:Event ):void
{
    page = page + 1;
}

public function doPrevious( event:Event ):void
{
    page = page - 1;
}

public function doThumb( event:Event ):void
{
    page = boxThumbs.getChildIndex( UIComponent( event.currentTarget ) );
}




public function get page():Number
{
    return _page;
}

// This really doesn't seem like the right place to do all this work
// For the purposes of this demonstration, this is where it landed
public function set page( value:Number ):void
{
    var gap:Number = boxForms.getStyle( "horizontalGap" );
    var form:Canvas = Canvas( boxForms.getChildAt( value ) );
    var child:UIComponent = null;
    var total:Number = 0;
    var thumb:Thumbnail = null;

    if( _page != value )
    {


        // Update the thumbnail for the current screen
        updateThumb( Canvas( boxForms.getChildAt( page ) ) );

        // Assign the new value
        _page = value;

        // Form pages
        cvsForms.filters = blurring;
        boxForms.x = 0 - ( value * ( form.width + gap ) );

        if( value == 0 )
        {
            btnPrevious.enabled = false;
            btnNext.enabled = true;
        } else if( value > 0 && value < ( boxForms.numChildren - 1 ) ) {
            btnPrevious.enabled = true;
            btnNext.enabled = true;
        } else {
            btnPrevious.enabled = true;
            btnNext.enabled = false;
        }

        // ButtonBar highlight
        for( var c:Number = 0; c < value; c++ )
        {
            child = UIComponent( btnMenu.getChildAt( c ) );
            total = total + child.width;
        }

        if( cvsHighlight.width == 0 && total != 0 )
        {
            cvsHighlight.visible = true;
        }

        cvsHighlight.width = total;

        // Thumbnail
        for( var t:Number = 0; t < boxThumbs.numChildren; t++ )
        {
            thumb = Thumbnail( boxThumbs.getChildAt( t ) );

            if( thumb == boxThumbs.getChildAt( value ) )
            {
                thumb.selected = true;
            } else {
                thumb.selected = false;
            }
        }

        // Set new help video

    }
}