package serve.serveup.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import serve.serveup.R;
import serve.serveup.dataholder.order.OrderStatusType;
import serve.serveup.dataholder.order.ReturnedOrder;
import serve.serveup.utils.Utils;
import serve.serveup.views.order.PickedOrderActivity;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersHolder> {

    private ArrayList<ReturnedOrder> orders;
    private ReturnedOrder pickedOrder;

    // Define the View Holder
    static class OrdersHolder extends RecyclerView.ViewHolder {
        LinearLayout cardOrderContainer;
        TextView cardOrderRestaurant;
        TextView cardOrderDatePurchased;
        TextView cardOrderDateFinished;
        TextView cardOrderPrice;
        TextView cardOrderStatus;

        OrdersHolder(final View itemView) {
            super(itemView);
            cardOrderContainer = itemView.findViewById(R.id.ordersCard);
            cardOrderRestaurant = itemView.findViewById(R.id.orderRestaurantNameText);
            cardOrderDatePurchased = itemView.findViewById(R.id.ordersDatumNarocilaField);
            cardOrderDateFinished = itemView.findViewById(R.id.ordersCasPrevzemaField);
            cardOrderPrice = itemView.findViewById(R.id.ordersPriceField);
            cardOrderStatus = itemView.findViewById(R.id.ordersStatusField);
        }
    }

    public OrdersAdapter(ArrayList<ReturnedOrder> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Find and inflate the appropriate card layout.
        View cardOrder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_order, parent, false);
        return new OrdersHolder(cardOrder);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrdersHolder holder, int position) {
        final ReturnedOrder order = this.orders.get(position);

        String casPrevzema = Utils.parseDateTimeString(order.getCasPrevzema())
                .split(" ")[1];

        final Context myContext = holder.cardOrderContainer.getContext();
        holder.cardOrderRestaurant.setText(order.getImeRestavracije());
        holder.cardOrderStatus.setText(OrderStatusType.getName(order.getStatus()));


        int orderStatusColor = R.color.searchIconColor;

        if(order.getStatus() == OrderStatusType.NOVO.getStatus())
            orderStatusColor = (R.color.order_status_new);
        else if (order.getStatus() == OrderStatusType.PRIPRAVLJENO.getStatus())
            orderStatusColor = R.color.order_status_prepared;

        if(order.getCheckedIn()) {
            holder.cardOrderStatus.setText(OrderStatusType.KONCANO.toString());
            orderStatusColor = R.color.order_status_finished;
        }

        holder.cardOrderStatus.setTextColor(myContext.getResources().getColor(orderStatusColor));

        holder.cardOrderDatePurchased.setText(Utils.parseDateTimeString(order.getCasNarocila()));
        holder.cardOrderDateFinished.setText(casPrevzema);
        holder.cardOrderPrice.setText(String.format("%.2f €", order.getCena()));

        holder.cardOrderContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickedOrder = orders.get(holder.getAdapterPosition());
                if (pickedOrder != null) {
                    Intent myIntent = new Intent(view.getContext(), PickedOrderActivity.class);
                    myIntent.putExtra("picked_order", pickedOrder);
                    view.getContext().startActivity(myIntent);
                }
                else
                    Utils.logInfo("Picked order doesnt exist");
            }
        });
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }
}
