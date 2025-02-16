package io.github.thecsdev.tcdcommons.api.client.gui.panel;

import io.github.thecsdev.tcdcommons.api.client.gui.TDrawContext;
import io.github.thecsdev.tcdcommons.api.client.gui.util.HorizontalAlignment;
import io.github.thecsdev.tcdcommons.api.client.gui.widget.TSelectWidget;
import net.minecraft.text.Text;

public class TMenuBarPanel extends TPanelElement
{
	// ==================================================
	public TMenuBarPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setScrollPadding(0);
		setScrollFlags(SCROLL_HORIZONTAL);
	}
	// --------------------------------------------------
	/**
	 * Used to calculate the X coordinate of the next
	 * {@link TMenuBarPanelItem} element.
	 */
	protected int nextX()
	{
		var last = getLastTChild(false);
		return last != null ? last.getTpeEndX() : getTpeX() + 1;
	}
	
	/**
	 * Used to calculate the width of the next
	 * {@link TMenuBarPanelItem} element.
	 */
	protected int nextW(Text label)
	{
		if(label == null) return 40;
		return getTextRenderer().getWidth(label) + 15;
	}
	// ==================================================
	/**
	 * Creates and adds a new {@link TMenuBarPanelItem} to this menu panel.
	 * @param label The {@link Text} shown on the menu item.
	 * @return The new menu item that was created and added.
	 */
	public TMenuBarPanelItem addItem(Text label)
	{
		var item = new TMenuBarPanelItem(label);
		addTChild(item, false);
		return item;
	}
	// ==================================================
	public class TMenuBarPanelItem extends TSelectWidget
	{
		// ----------------------------------------------
		protected TMenuBarPanelItem(Text label)
		{
			//construct
			super(TMenuBarPanel.this.nextX(), TMenuBarPanel.this.getTpeY() + 1,
					TMenuBarPanel.this.nextW(label), TMenuBarPanel.this.getTpeHeight() - 2);
			setMessage(label);
		}
		protected @Override void onOptionSelected(SWEntry option) { /*do not update the message*/ }
		// ----------------------------------------------
		public @Override void updateRenderingBoundingBox()
		{
			RENDER_RECT.setLocation(getTpeX(), getTpeY());
			RENDER_RECT.setSize(getTpeWidth(), getTpeHeight());
		}
		
		public @Override void render(TDrawContext pencil, int mouseX, int mouseY, float deltaTime)
		{
			pencil.drawTText(getMessage(), HorizontalAlignment.LEFT);
		}
		// ----------------------------------------------
		public @Override void postRender(TDrawContext pencil, int mouseX, int mouseY, float deltaTime)
		{
			if(isDropdownOpen()) pencil.drawTBorder(TPanelElement.COLOR_OUTLINE_FOCUSED);
			else if(isFocusedOrHovered()) pencil.drawTBorder(TPanelElement.COLOR_OUTLINE);
		}
		// ----------------------------------------------
	}
	// ==================================================
}